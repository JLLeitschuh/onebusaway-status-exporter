/**
 * Copyright (C) 2012 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onebusaway.status_exporter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import javax.servlet.Servlet;

import org.onebusaway.guice.jetty_exporter.JettyExporterModule;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.name.Names;

public class StatusJettyExporterModule extends AbstractModule {

  public static void addModuleAndDependencies(Set<Module> modules) {
    modules.add(new StatusJettyExporterModule());
    JettyExporterModule.addModuleAndDependencies(modules);
    StatusServiceModule.addModuleAndDependencies(modules);
  }

  @Override
  protected void configure() {

    bind(StatusServletSource.class);

    try {
      bind(URL.class).annotatedWith(Names.named(StatusServletSource.URL_NAME)).toInstance(
          new URL("http://localhost/status"));
    } catch (MalformedURLException e) {
      throw new IllegalStateException(e);
    }

    bind(Servlet.class).annotatedWith(
        Names.named(StatusServletSource.SERVLET_NAME)).to(StatusServlet.class);
  }

  /**
   * Implement hashCode() and equals() such that two instances of the module
   * will be equal.
   */
  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null)
      return false;
    return this.getClass().equals(o.getClass());
  }
}