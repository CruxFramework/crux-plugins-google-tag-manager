/*
 * Copyright 2015 cruxframework.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cruxframework.crux.plugin.google.gtm.client;

import org.cruxframework.crux.core.client.utils.JsUtils;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;

/**
 * Google Tag Manager API wrapper. 
 * @author Thiago da Rosa de Bustamante
 */
public class GoogleTagManager implements EntryPoint
{
	private static boolean initialized = false;
	private GTM gtmConstants = GWT.create(GTM.class);
	
	@Override
    public void onModuleLoad()
    {
		if (!initialized)
		{
			init(gtmConstants.containerID());
		}
    }
	
	/**
	 * Inform if the API was initialized
	 * @return true if initialized
	 */
	public static boolean isInitialized()
	{
		return initialized;
	}
		
	/**
	 * This method must be called once in your application, to initialize the google tag manager api script.
	 * @param userAccount
	 */
	public static void init(String containerID) 
	{
		assert (!initialized):"Google Tag Manager API was already initialized.";
		assert (containerID != null):"containerID can not be null. Please initialize Google Tag Manager API using a valid containerID.";
		Element firstScript = Document.get().getElementsByTagName("script").getItem(0);

		ScriptElement config = Document.get().createScriptElement(
				"var dataLayer = dataLayer || [];dataLayer.push({'gtm.start':new Date().getTime(),event:'gtm.js'});");

		firstScript.getParentNode().insertBefore(config, firstScript);
		ScriptElement script = Document.get().createScriptElement();

		script.setSrc("//www.googletagmanager.com/gtm.js?id="+containerID);
		script.setType("text/javascript");
		script.setAttribute("async", "true");
		firstScript.getParentNode().insertBefore(script, firstScript);
		initialized = true;
	}

	/**
	 * Push an event to the GTM dataLayer
	 * @param eventName event name
	 */
	public static void pushEvent(String eventName)
	{
		assert (initialized):"Google Tag Manager API was not initialized. Call init(containerID) method first,"
			+ " or configure GoogleTagManager EntryPoint on your .gwt.xml file";

		JavaScriptObject params = JavaScriptObject.createObject(); 
		JsUtils.writePropertyValue(params, "event", eventName);
		pushNative(params);
	}
	
	/**
	 * Push a variable to the GTM dataLayer
	 * @param variable variable name
	 * @param value new value for the given variable
	 */
	public static void pushVariable(String variable, String value)
	{
		assert (initialized):"Google Tag Manager API was not initialized. Call init(containerID) method first,"
			+ " or configure GoogleTagManager EntryPoint on your .gwt.xml file";

		JavaScriptObject params = JavaScriptObject.createObject(); 
		JsUtils.writePropertyValue(params, variable, value);
		pushNative(params);
	}
	
	public static void pushVariables(Variable... variables)
	{
		assert (initialized):"Google Tag Manager API was not initialized. Call init(containerID) method first,"
			+ " or configure GoogleTagManager EntryPoint on your .gwt.xml file";

		JavaScriptObject params = JavaScriptObject.createObject(); 
		
		for (Variable variable : variables)
        {
			JsUtils.writePropertyValue(params, variable.getName(), variable.getValue());
        }
		pushNative(params);
	}

	private static native void pushNative(JavaScriptObject param)/*-{
		$wnd.dataLayer.push(param);
	}-*/;

	/**
	 * Represents a variable to Google Tag Manager. Has a name and a string value
	 * @author Thiago da Rosa de Bustamante.
	 */
	public static class Variable
	{
		private String name;
		private String value;
		
		public Variable()
        {
        }

		public Variable(String name, String value)
        {
			this.name = name;
			this.value = value;
        }

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getValue()
		{
			return value;
		}

		public void setValue(String value)
		{
			this.value = value;
		}
	}
}
