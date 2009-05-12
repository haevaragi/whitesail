/*    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net
*/
/*
 * Created on Sep 3, 2005
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.FormInput;
import org.lobobrowser.html.parser.HtmlParser;
import org.lobobrowser.util.*;
import org.w3c.dom.*;
import org.w3c.dom.html2.*;


import java.io.*;
import java.util.*;
import java.util.logging.*;

public class HTMLElementImpl extends ElementImpl implements HTMLElement{	
	private final boolean noStyleSheet;
	
	public HTMLElementImpl(String name, boolean noStyleSheet) {
		super(name);
		this.noStyleSheet = noStyleSheet;
	}
	
	public HTMLElementImpl(String name) {
		super(name);
		this.noStyleSheet = false;
	}
	

	
	private Map computedStyles;
	
	
	public void setStyle(Object value) {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Cannot set style property");
	}	
	
	public void setCurrentStyle(Object value) {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Cannot set currentStyle property");
	}	

	public String getClassName() {
		String className = this.getAttribute("class");
		// Blank required instead of null.
		return className == null ? "" : className;
	}
	
	public void setClassName(String className) {
		this.setAttribute("class", className);
	}
	
	public String getCharset() {
		return this.getAttribute("charset");
	}

	public void setCharset(String charset) {
		this.setAttribute("charset", charset);
	}

	public void warn(String message, Throwable err) {
		logger.log(Level.WARNING, message, err);
	}

	public void warn(String message) {
		logger.log(Level.WARNING, message);
	}

	protected int getAttributeAsInt(String name, int defaultValue) {
		String value = this.getAttribute(name);
		try {
			return Integer.parseInt(value);
		} catch(Exception err) {
			this.warn("Bad integer", err);
			return defaultValue;
		}
	}
	
	public boolean getAttributeAsBoolean(String name) {
		return this.getAttribute(name) != null;
	}


	private boolean isMouseOver = false;
	
	public void setMouseOver(boolean mouseOver) {
		if(this.isMouseOver != mouseOver) {
			// Change isMouseOver field before checking to invalidate.
			this.isMouseOver = mouseOver;
			// Check if descendents are affected (e.g. div:hover a { ... } )
			this.invalidateDescendentsForHover();	
		}
	}

	private void invalidateDescendentsForHover() {
		synchronized(this.treeLock) {
			this.invalidateDescendentsForHoverImpl(this);
		}
	}
	
	private void invalidateDescendentsForHoverImpl(HTMLElementImpl ancestor) {
		ArrayList nodeList = this.nodeList;
		if(nodeList != null) {
			int size = nodeList.size();
			for(int i = 0; i < size; i++) {
				Object node = nodeList.get(i);
				if(node instanceof HTMLElementImpl) {
					HTMLElementImpl descendent = (HTMLElementImpl) node;
					
					descendent.invalidateDescendentsForHoverImpl(ancestor);
				}
			}
		}
	}
	

	

	

	/**
	 * Gets the pseudo-element lowercase names currently
	 * applicable to this element. Method must return
	 * <code>null</code> if there are no such
	 * pseudo-elements.
	 */
	public Set getPseudoNames() {
		Set pnset = null;
		if(this.isMouseOver) {
			if(pnset == null) {
				pnset = new HashSet(1);
			}
			pnset.add("hover");
		}
		return pnset;
	}
	
	
	

	/**
	 * Gets form input due to the current element. It should
	 * return <code>null</code> except when the element is a form input element.
	 */
	protected FormInput[] getFormInputs() {
		// Override in input elements
		return null;
	}
	
	private boolean classMatch(String classTL) {
		String classNames = this.getClassName();
		if(classNames == null || classNames.length() == 0) {
			return classTL == null;
		}
		StringTokenizer tok = new StringTokenizer(classNames, " \t\r\n");
		while(tok.hasMoreTokens()) {
			String token = tok.nextToken();
			if(token.toLowerCase().equals(classTL)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get an ancestor that matches the element tag name given and the
	 * style class given.
	 * @param elementTL An tag name in lowercase or an asterisk (*).
	 * @param classTL A class name in lowercase.
	 */
	public HTMLElementImpl getAncestorWithClass(String elementTL, String classTL) {
		Object nodeObj = this.getParentNode();
		if(nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			String pelementTL = parentElement.getTagName().toLowerCase();
			if(("*".equals(elementTL) || elementTL.equals(pelementTL)) && parentElement.classMatch(classTL)) {
				return parentElement;
			}
			return parentElement.getAncestorWithClass(elementTL, classTL);
		}
		else {
			return null;
		}
	}

	public HTMLElementImpl getParentWithClass(String elementTL, String classTL) {
		Object nodeObj = this.getParentNode();
		if(nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			String pelementTL = parentElement.getTagName().toLowerCase();
			if(("*".equals(elementTL) || elementTL.equals(pelementTL)) && parentElement.classMatch(classTL)) {
				return parentElement;
			}
		}
		return null;
	}

	public HTMLElementImpl getPreceedingSiblingElement() {
		Node parentNode = this.getParentNode();
		if(parentNode == null) {
			return null;
		}
		NodeList childNodes = parentNode.getChildNodes();
		if(childNodes == null) {
			return null;
		}
		int length = childNodes.getLength();
		HTMLElementImpl priorElement = null;
		for(int i = 0; i < length; i++) {
			Node child = childNodes.item(i);
			if(child == this) {
				return priorElement;
			}
			if(child instanceof HTMLElementImpl) {
				priorElement = (HTMLElementImpl) child;
			}
		}
		return null;
	}
	
	public HTMLElementImpl getPreceedingSiblingWithClass(String elementTL, String classTL) {
		HTMLElementImpl psibling = this.getPreceedingSiblingElement();
		if(psibling != null) {
			String pelementTL = psibling.getTagName().toLowerCase();
			if(("*".equals(elementTL) || elementTL.equals(pelementTL)) && psibling.classMatch(classTL)) {
				return psibling;
			}
		}
		return null;
	}

	public HTMLElementImpl getAncestorWithId(String elementTL, String idTL) {
		Object nodeObj = this.getParentNode();
		if(nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			String pelementTL = parentElement.getTagName().toLowerCase();
			String pid = parentElement.getId();
			String pidTL = pid == null ? null : pid.toLowerCase();
			if(("*".equals(elementTL) || elementTL.equals(pelementTL)) && idTL.equals(pidTL)) {
				return parentElement;
			}
			return parentElement.getAncestorWithId(elementTL, idTL);
		}
		else {
			return null;
		}
	}

	public HTMLElementImpl getParentWithId(String elementTL, String idTL) {
		Object nodeObj = this.getParentNode();
		if(nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			String pelementTL = parentElement.getTagName().toLowerCase();
			String pid = parentElement.getId();
			String pidTL = pid == null ? null : pid.toLowerCase();
			if(("*".equals(elementTL) || elementTL.equals(pelementTL)) && idTL.equals(pidTL)) {
				return parentElement;
			}
		}
		return null;
	}

	public HTMLElementImpl getPreceedingSiblingWithId(String elementTL, String idTL) {
		HTMLElementImpl psibling = this.getPreceedingSiblingElement();
		if(psibling != null) {
			String pelementTL = psibling.getTagName().toLowerCase();
			String pid = psibling.getId();
			String pidTL = pid == null ? null : pid.toLowerCase();
			if(("*".equals(elementTL) || elementTL.equals(pelementTL)) && idTL.equals(pidTL)) {
				return psibling;
			}
		}
		return null;
	}

	public HTMLElementImpl getAncestor(String elementTL) {
		Object nodeObj = this.getParentNode();
		if(nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			if("*".equals(elementTL)) {
				return parentElement;
			}
			String pelementTL = parentElement.getTagName().toLowerCase();
			if(elementTL.equals(pelementTL)) {
				return parentElement;
			}
			return parentElement.getAncestor(elementTL);
		}
		else {
			return null;
		}
	}	

	public HTMLElementImpl getParent(String elementTL) {
		Object nodeObj = this.getParentNode();
		if(nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			if("*".equals(elementTL)) {
				return parentElement;
			}
			String pelementTL = parentElement.getTagName().toLowerCase();
			if(elementTL.equals(pelementTL)) {
				return parentElement;
			}
		}
		return null;
	}	

	public HTMLElementImpl getPreceedingSibling(String elementTL) {
		HTMLElementImpl psibling = this.getPreceedingSiblingElement();
		if(psibling != null) {
			if("*".equals(elementTL)) {
				return psibling;
			}
			String pelementTL = psibling.getTagName().toLowerCase();
			if(elementTL.equals(pelementTL)) {
				return psibling;
			}
		}
		return null;
	}	

	protected Object getAncestorForJavaClass(Class javaClass) {
		Object nodeObj = this.getParentNode();
		if(nodeObj == null || javaClass.isInstance(nodeObj)) {
			return nodeObj;
		}
		else if(nodeObj instanceof HTMLElementImpl) {
			return ((HTMLElementImpl) nodeObj).getAncestorForJavaClass(javaClass);
		}
		else {
			return null;
		}
	}
	
	public void setInnerHTML(String newHtml) {
		HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
		if(document == null) {
			this.warn("setInnerHTML(): Element " + this + " does not belong to a document.");
			return;
		}
		HtmlParser parser = new HtmlParser(document.getUserAgentContext(), document, null, null, null);
		synchronized(this) {
			ArrayList nl = this.nodeList;
			if (nl != null) {
				nl.clear();
			}
		}
		// Should not synchronize around parser probably.
		try {
			Reader reader = new StringReader(newHtml);
			try {
				parser.parse(reader, this);
			} finally {
				reader.close();
			}
		} catch(Exception thrown) {
			this.warn("setInnerHTML(): Error setting inner HTML.", thrown);
		}
	}

	public String getOuterHTML() {
		StringBuffer buffer = new StringBuffer();
		synchronized(this) {
			this.appendOuterHTMLImpl(buffer);
		}
		return buffer.toString();
	}

	protected void appendOuterHTMLImpl(StringBuffer buffer) {
		String tagName = this.getTagName();
		buffer.append('<');
		buffer.append(tagName);
		Map attributes = this.attributes;
		if(attributes != null) {
			Iterator i = attributes.entrySet().iterator();
			while(i.hasNext()) {
				Map.Entry entry = (Map.Entry) i.next();
				String value = (String) entry.getValue();
				if(value != null) {
					buffer.append(' ');
					buffer.append(entry.getKey());
					buffer.append("=\"");
					buffer.append(Strings.strictHtmlEncode(value, true));
					buffer.append("\"");				
				}
			}
		}
		ArrayList nl = this.nodeList;
		if(nl == null || nl.size() == 0) {
			buffer.append("/>");
			return;
		}
		buffer.append('>');
		this.appendInnerHTMLImpl(buffer);
		buffer.append("</");
		buffer.append(tagName);
		buffer.append('>');
	}	


	
	public int getOffsetTop() {
		//TODO: Sometimes this can be called while parsing, and
		//browsers generally give the right answer.
		UINode uiNode = this.getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().y;
	}
	
	public int getOffsetLeft() {
		UINode uiNode = this.getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().x;
	}

	public int getOffsetWidth() {
		UINode uiNode = this.getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().width;
	}

	public int getOffsetHeight() {
		UINode uiNode = this.getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().height;
	}


	
	public String getDocumentBaseURI() {
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if(doc != null) {
			return doc.getBaseURI();
		}
		else {
			return null;
		}
	}

	public String toString() {
		return super.toString();
	}
}
