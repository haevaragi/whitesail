/*
    GNU LESSER GENERAL PUBLIC LICENSE
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
package org.lobobrowser.html.domimpl;


import org.w3c.dom.html2.HTMLFontElement;

public class HTMLFontElementImpl extends HTMLAbstractUIElement implements
		HTMLFontElement {
	public HTMLFontElementImpl(String name) {
		super(name);
	}

	public String getColor() {
		return this.getAttribute("color");
	}

	public String getFace() {
		return this.getAttribute("face");
	}

	public String getSize() {
		return this.getAttribute("size");
	}

	public void setColor(String color) {
		this.setAttribute("color", color);
	}

	public void setFace(String face) {
		this.setAttribute("face", face);
	}

	public void setSize(String size) {
		this.setAttribute("size", size);
	}

}
