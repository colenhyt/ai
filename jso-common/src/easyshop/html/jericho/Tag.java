// Jericho HTML Parser - Java based library for analysing and manipulating HTML
// Version 1.5
// Copyright (C) 2004 Martin Jericho
// http://jerichohtml.sourceforge.net/
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// http://www.gnu.org/copyleft/lesser.html
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package easyshop.html.jericho;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Represents either a {@link StartTag} or {@link EndTag}.
 * <p>
 * The constants can be used in methods that accept a <i><code>name</code></i> parameter.
 *
 * @see Source#getNextTagIterator(int pos)
 */
public abstract class Tag extends Segment {
	String name=null; // always lower case, can always use == operator to compare with constants below

	protected static final String COMMENT="!--";

	/** <a target="_blank" href="http://www.w3.org/TR/REC-xml#sec-pi">processing instruction</a> (<code>&lt;&#63; &#46;&#46;&#46; &#63;&gt;</code>) */
	public static final String PROCESSING_INSTRUCTION="?";

	/** <a target="_blank" href="http://www.w3.org/TR/REC-xml/#sec-prolog-dtd">XML declaration</a> (<code>&lt;&#63;xml &#46;&#46;&#46; &#63;&gt;</code>) */
	public static final String XML_DECLARATION="?xml";

	/** <a target="_blank" href="http://www.w3.org/TR/REC-xml#dt-doctype">document type declaration</a> (<code>&lt;&#33;DOCTYPE &#46;&#46;&#46; &gt;</code>) */
	public static final String DOCTYPE_DECLARATION="!doctype";

	/** Standard <a target="_blank" href="http://www.php.net">PHP</a> tag (<code>&lt;&#63;php &#46;&#46;&#46; &#63;&gt;</code>) */
	public static final String SERVER_PHP="?php";

	/** Any one of an <a target="_blank" href="http://msdn.microsoft.com/asp/">ASP</a>, <a target="_blank" href="http://java.sun.com/products/jsp/">JSP</a>, <a target="_blank" href="http://www.modpython.org/">PSP</a>, <a target="_blank" href="http://au2.php.net/manual/en/configuration.directives.php#ini.asp-tags">ASP-style PHP</a> or <a target="_blank" href="http://www.masonbook.com/book/chapter-2.mhtml#CHP-2-SECT-3.1">Mason substitution</a> tag (<code>&lt;% &#46;&#46;&#46; %&gt;</code>) */
	public static final String SERVER_COMMON=new String("%");

	/** <a target="_blank" href="http://www.masonbook.com/book/chapter-2.mhtml#CHP-2-SECT-3.4">Mason named block</a> (<code>&lt;%<i>name</i> &#46;&#46;&#46; &gt; &#46;&#46;&#46; &lt;/%<i>name</i>&gt;</code>) */
	public static final String SERVER_MASON_NAMED_BLOCK=new String("%"); // NOTE: this value is the same value as SERVER_COMMON

	/** <a target="_blank" href="http://www.masonbook.com/book/chapter-2.mhtml#CHP-2-SECT-3.3">Mason component call</a> (<code>&lt;&amp; &#46;&#46;&#46; &amp;&gt;</code>) */
	public static final String SERVER_MASON_COMPONENT_CALL="&";

	/** <a target="_blank" href="http://www.masonbook.com/book/chapter-2.mhtml#CHP-2-SECT-3.3.1">Mason component called with content</a> (<code>&lt;&amp;| &#46;&#46;&#46; &amp;&gt; &#46;&#46;&#46; &lt;/&amp;&gt;</code>) */
	public static final String SERVER_MASON_COMPONENT_CALLED_WITH_CONTENT="&|";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/links.html#edef-A">HTML 4.01 definition</a> */
	public static final String A="a";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-ABBR">HTML 4.01 definition</a> */
	public static final String ABBR="abbr";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-ACRONYM">HTML 4.01 definition</a> */
	public static final String ACRONYM="acronym";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/global.html#edef-ADDRESS">HTML 4.01 definition</a> */
	public static final String ADDRESS="address";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/objects.html#edef-APPLET">HTML 4.01 definition</a> */
	public static final String APPLET="applet";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/objects.html#edef-AREA">HTML 4.01 definition</a> */
	public static final String AREA="area";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/graphics.html#edef-B">HTML 4.01 definition</a> */
	public static final String B="b";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/links.html#edef-BASE">HTML 4.01 definition</a> */
	public static final String BASE="base";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/graphics.html#edef-BASEFONT">HTML 4.01 definition</a> */
	public static final String BASEFONT="basefont";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/dirlang.html#edef-BDO">HTML 4.01 definition</a> */
	public static final String BDO="bdo";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/graphics.html#edef-BIG">HTML 4.01 definition</a> */
	public static final String BIG="big";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-BLOCKQUOTE">HTML 4.01 definition</a> */
	public static final String BLOCKQUOTE="blockquote";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/global.html#edef-BODY">HTML 4.01 definition</a> */
	public static final String BODY="body";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-BR">HTML 4.01 definition</a> */
	public static final String BR="br";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/interact/forms.html#edef-BUTTON">HTML 4.01 definition</a> */
	public static final String BUTTON="button";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/tables.html#edef-CAPTION">HTML 4.01 definition</a> */
	public static final String CAPTION="caption";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/graphics.html#edef-CENTER">HTML 4.01 definition</a> */
	public static final String CENTER="center";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-CITE">HTML 4.01 definition</a> */
	public static final String CITE="cite";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-CODE">HTML 4.01 definition</a> */
	public static final String CODE="code";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/tables.html#edef-COL">HTML 4.01 definition</a> */
	public static final String COL="col";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/tables.html#edef-COLGROUP">HTML 4.01 definition</a> */
	public static final String COLGROUP="colgroup";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/lists.html#edef-DD">HTML 4.01 definition</a> */
	public static final String DD="dd";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-DEL">HTML 4.01 definition</a> */
	public static final String DEL="del";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-DFN">HTML 4.01 definition</a> */
	public static final String DFN="dfn";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/lists.html#edef-DIR">HTML 4.01 definition</a> */
	public static final String DIR="dir";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/global.html#edef-DIV">HTML 4.01 definition</a> */
	public static final String DIV="div";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/lists.html#edef-DL">HTML 4.01 definition</a> */
	public static final String DL="dl";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/lists.html#edef-DT">HTML 4.01 definition</a> */
	public static final String DT="dt";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-EM">HTML 4.01 definition</a> */
	public static final String EM="em";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/interact/forms.html#edef-FIELDSET">HTML 4.01 definition</a> */
	public static final String FIELDSET="fieldset";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/graphics.html#edef-FONT">HTML 4.01 definition</a> */
	public static final String FONT="font";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/interact/forms.html#edef-FORM">HTML 4.01 definition</a> */
	public static final String FORM="form";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/frames.html#edef-FRAME">HTML 4.01 definition</a> */
	public static final String FRAME="frame";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/frames.html#edef-FRAMESET">HTML 4.01 definition</a> */
	public static final String FRAMESET="frameset";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/global.html#edef-H1">HTML 4.01 definition</a> */
	public static final String H1="h1";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/global.html#edef-H2">HTML 4.01 definition</a> */
	public static final String H2="h2";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/global.html#edef-H3">HTML 4.01 definition</a> */
	public static final String H3="h3";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/global.html#edef-H4">HTML 4.01 definition</a> */
	public static final String H4="h4";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/global.html#edef-H5">HTML 4.01 definition</a> */
	public static final String H5="h5";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/global.html#edef-H6">HTML 4.01 definition</a> */
	public static final String H6="h6";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/global.html#edef-HEAD">HTML 4.01 definition</a> */
	public static final String HEAD="head";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/graphics.html#edef-HR">HTML 4.01 definition</a> */
	public static final String HR="hr";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/global.html#edef-HTML">HTML 4.01 definition</a> */
	public static final String HTML="html";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/graphics.html#edef-I">HTML 4.01 definition</a> */
	public static final String I="i";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/frames.html#edef-IFRAME">HTML 4.01 definition</a> */
	public static final String IFRAME="iframe";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/objects.html#edef-IMG">HTML 4.01 definition</a> */
	public static final String IMG="img";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/interact/forms.html#edef-INPUT">HTML 4.01 definition</a> */
	public static final String INPUT="input";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-INS">HTML 4.01 definition</a> */
	public static final String INS="ins";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/interact/forms.html#edef-ISINDEX">HTML 4.01 definition</a> */
	public static final String ISINDEX="isindex";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-KBD">HTML 4.01 definition</a> */
	public static final String KBD="kbd";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/interact/forms.html#edef-LABEL">HTML 4.01 definition</a> */
	public static final String LABEL="label";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/interact/forms.html#edef-LEGEND">HTML 4.01 definition</a> */
	public static final String LEGEND="legend";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/lists.html#edef-LI">HTML 4.01 definition</a> */
	public static final String LI="li";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/links.html#edef-LINK">HTML 4.01 definition</a> */
	public static final String LINK="link";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/objects.html#edef-MAP">HTML 4.01 definition</a> */
	public static final String MAP="map";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/lists.html#edef-MENU">HTML 4.01 definition</a> */
	public static final String MENU="menu";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/global.html#edef-META">HTML 4.01 definition</a> */
	public static final String META="meta";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/frames.html#edef-NOFRAMES">HTML 4.01 definition</a> */
	public static final String NOFRAMES="noframes";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/interact/scripts.html#edef-NOSCRIPT">HTML 4.01 definition</a> */
	public static final String NOSCRIPT="noscript";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/objects.html#edef-OBJECT">HTML 4.01 definition</a> */
	public static final String OBJECT="object";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/lists.html#edef-OL">HTML 4.01 definition</a> */
	public static final String OL="ol";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/interact/forms.html#edef-OPTGROUP">HTML 4.01 definition</a> */
	public static final String OPTGROUP="optgroup";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/interact/forms.html#edef-OPTION">HTML 4.01 definition</a> */
	public static final String OPTION="option";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-P">HTML 4.01 definition</a> */
	public static final String P="p";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/objects.html#edef-PARAM">HTML 4.01 definition</a> */
	public static final String PARAM="param";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-PRE">HTML 4.01 definition</a> */
	public static final String PRE="pre";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-Q">HTML 4.01 definition</a> */
	public static final String Q="q";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/graphics.html#edef-S">HTML 4.01 definition</a> */
	public static final String S="s";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-SAMP">HTML 4.01 definition</a> */
	public static final String SAMP="samp";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/interact/scripts.html#edef-SCRIPT">HTML 4.01 definition</a> */
	public static final String SCRIPT="script";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/interact/forms.html#edef-SELECT">HTML 4.01 definition</a> */
	public static final String SELECT="select";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/graphics.html#edef-SMALL">HTML 4.01 definition</a> */
	public static final String SMALL="small";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/global.html#edef-SPAN">HTML 4.01 definition</a> */
	public static final String SPAN="span";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/graphics.html#edef-STRIKE">HTML 4.01 definition</a> */
	public static final String STRIKE="strike";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-STRONG">HTML 4.01 definition</a> */
	public static final String STRONG="strong";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/styles.html#edef-STYLE">HTML 4.01 definition</a> */
	public static final String STYLE="style";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-SUB">HTML 4.01 definition</a> */
	public static final String SUB="sub";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-SUP">HTML 4.01 definition</a> */
	public static final String SUP="sup";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/tables.html#edef-TABLE">HTML 4.01 definition</a> */
	public static final String TABLE="table";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/tables.html#edef-TBODY">HTML 4.01 definition</a> */
	public static final String TBODY="tbody";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/tables.html#edef-TD">HTML 4.01 definition</a> */
	public static final String TD="td";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/interact/forms.html#edef-TEXTAREA">HTML 4.01 definition</a> */
	public static final String TEXTAREA="textarea";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/tables.html#edef-TFOOT">HTML 4.01 definition</a> */
	public static final String TFOOT="tfoot";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/tables.html#edef-TH">HTML 4.01 definition</a> */
	public static final String TH="th";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/tables.html#edef-THEAD">HTML 4.01 definition</a> */
	public static final String THEAD="thead";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/global.html#edef-TITLE">HTML 4.01 definition</a> */
	public static final String TITLE="title";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/tables.html#edef-TR">HTML 4.01 definition</a> */
	public static final String TR="tr";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/graphics.html#edef-TT">HTML 4.01 definition</a> */
	public static final String TT="tt";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/present/graphics.html#edef-U">HTML 4.01 definition</a> */
	public static final String U="u";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/lists.html#edef-UL">HTML 4.01 definition</a> */
	public static final String UL="ul";

	/** <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#edef-VAR">HTML 4.01 definition</a> */
	public static final String VAR="var";

	private static final TagSet END_TAG_FORBIDDEN_SET=new TagSet(new String[] {AREA,BASE,BASEFONT,BR,COL,FRAME,HR,IMG,INPUT,ISINDEX,LINK,META,PARAM});
	private static final TagSet BLOCK_SET=new TagSet(new String[] {P,H1,H2,H3,H4,H5,H6,UL,OL,DIR,MENU,PRE,DL,DIV,CENTER,NOSCRIPT,NOFRAMES,BLOCKQUOTE,FORM,ISINDEX,HR,TABLE,FIELDSET,ADDRESS});
	private static final TagSet INLINE_SET=new TagSet(new String[] {TT,I,B,U,S,STRIKE,BIG,SMALL,EM,STRONG,DFN,CODE,SAMP,KBD,VAR,CITE,ABBR,ACRONYM,A,IMG,APPLET,OBJECT,FONT,BASEFONT,BR,SCRIPT,MAP,Q,SUB,SUP,SPAN,BDO,IFRAME,INPUT,SELECT,TEXTAREA,LABEL,BUTTON,INS,DEL});

	private static final TagSet _HTML=new TagSet().union(HTML);
	private static final TagSet _TABLE=new TagSet().union(TABLE);
	private static final TagSet _DL=new TagSet().union(DL);
	private static final TagSet _LI=new TagSet().union(LI);
	private static final TagSet _UL_OL=new TagSet().union(UL).union(OL);
	private static final TagSet _DD_DT=new TagSet().union(DD).union(DT);
	private static final TagSet _BODY_FRAMESET=new TagSet().union(BODY).union(FRAMESET);
	private static final TagSet _THEAD_TBODY_TFOOT_TR=new TagSet().union(THEAD).union(TBODY).union(TFOOT).union(TR);
	private static final TagSet _THEAD_TBODY_TFOOT_TR_COLGROUP=new TagSet().union(_THEAD_TBODY_TFOOT_TR).union(COLGROUP);
	private static final TagSet _THEAD_TBODY_TFOOT_TR_TD_TH=new TagSet().union(_THEAD_TBODY_TFOOT_TR).union(TD).union(TH);
	private static final TagSet _THEAD_TBODY_TFOOT_TR_TABLE=new TagSet().union(_THEAD_TBODY_TFOOT_TR).union(TABLE);
	private static final TagSet _THEAD_TBODY_TFOOT_TABLE=new TagSet().union(_THEAD_TBODY_TFOOT_TR_TABLE).minus(TR);
	private static final TagSet _OPTION_OPTGROUP=new TagSet().union(OPTION).union(OPTGROUP);
	private static final TagSet _SELECT=new TagSet().union(SELECT);
	private static final TagSet _TBODY_TFOOT=new TagSet().union(TBODY).union(TFOOT);
	private static final TagSet _TBODY_THEAD=new TagSet().union(TBODY).union(THEAD);
	private static final TagSet _BLOCKSET_DL_DD_DT_TABLE_TH_TD_LI=new TagSet().union(BLOCK_SET).union(DL).union(_DD_DT).union(TABLE).union(TH).union(TD).union(LI);
	private static final TagSet _BLOCKSET_DL_DD_DT_TABLE_BODY_HTML_THEAD_TBODY_TFOOT_TR_TD_TH_CAPTION_LEGEND=new TagSet().union(BLOCK_SET).union(DL).union(_DD_DT).union(TABLE).union(BODY).union(HTML).union(_THEAD_TBODY_TFOOT_TR_TD_TH).union(CAPTION).union(LEGEND);

	private static final TerminatorSets DEFINITION_TERMINATOR_SETS=new TerminatorSets(_DD_DT, _DL, _DL);
	private static final TerminatorSets TABLE_CELL_TERMINATOR_SETS=new TerminatorSets(_THEAD_TBODY_TFOOT_TR_TD_TH, _THEAD_TBODY_TFOOT_TR_TABLE, _TABLE);
	private static final TerminatorSets TABLE_SECTION_TERMINATOR_SETS=new TerminatorSets(_TBODY_THEAD, _TABLE, _TABLE);

	private static final HashMap TAG_MAP=buildTagMap(); // contains a map of tag names to the tag constants, allowing recognised tags to be tested using == operator instead of equals()
	private static final HashMap OPTIONAL_END_TAG_TERMINATOR_SETS_MAP=buildOptionalEndTagTerminatorSetsMap(); // contains a map of tags having optional end tags to the TerminatorSets that can terminate the element if the end tag is not present

	/**
	 * Returns the name of the tag, always in lower case.
	 * @return the name of the tag.
	 */
	public String getName() {
		return name;
	}

	Tag(Source source, int begin, int end, String name) {
		super(source, begin, end);
		this.name=getTagConstant(name.toLowerCase());
	}

	Tag() {} // used when creating CACHED_NULL objects

	private String getTagConstant(String name) {
		String tagConstant=(String)TAG_MAP.get(name);
		return tagConstant!=null ? tagConstant : name;
	}

	TerminatorSets getOptionalEndTagTerminatorSets() {
		return (TerminatorSets)OPTIONAL_END_TAG_TERMINATOR_SETS_MAP.get(name);
	}

	protected static boolean isEndTagForbidden(String name) {
		return END_TAG_FORBIDDEN_SET.contains(name.toLowerCase());
	}

	protected static boolean isEndTagOptional(String name) {
		return OPTIONAL_END_TAG_TERMINATOR_SETS_MAP.containsKey(name.toLowerCase());
	}

	protected static boolean isEndTagRequired(String name) {
		return !isEndTagForbidden(name) && !isEndTagOptional(name);
	}

	protected static boolean isBlock(String name) {
		return BLOCK_SET.contains(name.toLowerCase());
	}

	protected static boolean isInline(String name) {
		return INLINE_SET.contains(name.toLowerCase());
	}

	protected static Iterator getNextTagIterator(Source source, int pos) {
		return new NextTagIterator(source,pos);
	}

	private static class NextTagIterator implements Iterator {
		private Source source;
		private Tag nextTag=null;
		private Tag cachedTagAfterNext;
		private boolean cachedTagAfterNextIsStartTag=false;

		public NextTagIterator(Source source, int pos) {
			this.source=source;
			Segment enclosingComment=source.findEnclosingComment(pos);
			if (enclosingComment!=null) pos=enclosingComment.end;
			cachedTagAfterNext=source.findNextEndTag(pos);
			loadNextTag(pos);
		}

		public boolean hasNext() {
			return nextTag!=null;
		}

		public Object next() {
			if (!hasNext()) throw new NoSuchElementException();
			Tag result=nextTag;
			loadNextTag(result.end);
			return result;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		private void loadNextTag(int pos) {
			nextTag=cachedTagAfterNextIsStartTag ? (Tag)source.findNextEndTag(pos) : (Tag)source.findNextStartTag(pos);
			if (cachedTagAfterNext==null) return;
			if (nextTag!=null && nextTag.begin<cachedTagAfterNext.begin) {
				if (!cachedTagAfterNextIsStartTag && ((StartTag)nextTag).isComment() && cachedTagAfterNext.begin<nextTag.end) // if this start tag is a comment and the next end tag is inside it
					cachedTagAfterNext=source.findNextEndTag(nextTag.end); // then find the next end tag outside of the comment.
				return;
			}
			Tag newNextTag=cachedTagAfterNext;
			cachedTagAfterNext=nextTag;
			nextTag=newNextTag;
			cachedTagAfterNextIsStartTag=!cachedTagAfterNextIsStartTag;
		}
	}

	static final class TerminatorSets {
		private Set startTagTerminatorSet; // Set of start tags that terminate the element
		private Set endTagTerminatorSet; // Set of end tags that terminate the element (the end tag of this element is assumed and not included in this set)
		private Set ignoredNestedElementSet; // Set of elements that can be nested within this element, which may contain tags from startTagTerminatorSet and endTagTerminatorSet that must be ignored

		public TerminatorSets(TagSet startTagTerminatorSet, TagSet endTagTerminatorSet, TagSet ignoredNestedElementSet) {
			this.startTagTerminatorSet=startTagTerminatorSet;
			this.endTagTerminatorSet=endTagTerminatorSet;
			this.ignoredNestedElementSet=ignoredNestedElementSet;
		}

		public Set getStartTagTerminatorSet() {
			return startTagTerminatorSet;
		}
		public Set getEndTagTerminatorSet() {
			return endTagTerminatorSet;
		}
		public Set getIgnoredNestedElementSet() {
			return ignoredNestedElementSet;
		}
	}

	private static final class TagSet extends HashSet {
		public TagSet() {}
		public TagSet(String[] items) {
			super(1+(int)(items.length*1.4));
			for (int i=0; i<items.length; i++) add(items[i]);
		}
		public TagSet union(String item) {
			add(item);
			return this;
		}
		public TagSet union(Collection collection) {
			for (Iterator i=collection.iterator(); i.hasNext();) add(i.next());
			return this;
		}
		public TagSet minus(String item) {
			remove(item);
			return this;
		}
	}

	private static HashMap buildOptionalEndTagTerminatorSetsMap() {
		// HTML is included in the IgnoredNestedElementSet of BODY and HTML in case the source contains (illegaly) nested HTML documents
		HashMap map=new HashMap(20,1.0F); // 15 entries in total
		map.put(BODY,new TerminatorSets(null, _HTML, _HTML));
		map.put(COLGROUP,new TerminatorSets(_THEAD_TBODY_TFOOT_TR_COLGROUP, _TABLE, _TABLE));
		map.put(DD,DEFINITION_TERMINATOR_SETS);
		map.put(DT,DEFINITION_TERMINATOR_SETS);
		map.put(HEAD,new TerminatorSets(_BODY_FRAMESET, _HTML, null));
		map.put(HTML,new TerminatorSets(null, null, _HTML));
		map.put(LI,new TerminatorSets(_LI, _UL_OL, _UL_OL));
		map.put(OPTION,new TerminatorSets(_OPTION_OPTGROUP, _SELECT, null));
		map.put(P,new TerminatorSets(_BLOCKSET_DL_DD_DT_TABLE_TH_TD_LI, _BLOCKSET_DL_DD_DT_TABLE_BODY_HTML_THEAD_TBODY_TFOOT_TR_TD_TH_CAPTION_LEGEND, null));
		map.put(TBODY,new TerminatorSets(_TBODY_TFOOT, _TABLE, _TABLE)); // TFOOT is included in StartTagTerminatorSet although correct HTML requires TFOOT to precede TBODY
		map.put(TD,TABLE_CELL_TERMINATOR_SETS);
		map.put(TFOOT,TABLE_SECTION_TERMINATOR_SETS);
		map.put(TH,TABLE_CELL_TERMINATOR_SETS);
		map.put(THEAD,TABLE_SECTION_TERMINATOR_SETS);
		map.put(TR,new TerminatorSets(_THEAD_TBODY_TFOOT_TR, _THEAD_TBODY_TFOOT_TABLE, _TABLE));
		return map;
	}

	private static HashMap buildTagMap() {
		HashMap map=new HashMap(132,1.0F); // 99 tags total
		map.put(COMMENT,COMMENT);
		map.put(PROCESSING_INSTRUCTION,PROCESSING_INSTRUCTION);
		map.put(XML_DECLARATION,XML_DECLARATION);
		map.put(DOCTYPE_DECLARATION,DOCTYPE_DECLARATION);
		map.put(SERVER_PHP,SERVER_PHP);
		map.put(SERVER_COMMON,SERVER_COMMON);
		map.put(SERVER_MASON_COMPONENT_CALL,SERVER_MASON_COMPONENT_CALL);
		map.put(SERVER_MASON_COMPONENT_CALLED_WITH_CONTENT,SERVER_MASON_COMPONENT_CALLED_WITH_CONTENT);
		map.put(A,A);
		map.put(ABBR,ABBR);
		map.put(ACRONYM,ACRONYM);
		map.put(ADDRESS,ADDRESS);
		map.put(APPLET,APPLET);
		map.put(AREA,AREA);
		map.put(B,B);
		map.put(BASE,BASE);
		map.put(BASEFONT,BASEFONT);
		map.put(BDO,BDO);
		map.put(BIG,BIG);
		map.put(BLOCKQUOTE,BLOCKQUOTE);
		map.put(BODY,BODY);
		map.put(BR,BR);
		map.put(BUTTON,BUTTON);
		map.put(CAPTION,CAPTION);
		map.put(CENTER,CENTER);
		map.put(CITE,CITE);
		map.put(CODE,CODE);
		map.put(COL,COL);
		map.put(COLGROUP,COLGROUP);
		map.put(DD,DD);
		map.put(DEL,DEL);
		map.put(DFN,DFN);
		map.put(DIR,DIR);
		map.put(DIV,DIV);
		map.put(DL,DL);
		map.put(DT,DT);
		map.put(EM,EM);
		map.put(FIELDSET,FIELDSET);
		map.put(FONT,FONT);
		map.put(FORM,FORM);
		map.put(FRAME,FRAME);
		map.put(FRAMESET,FRAMESET);
		map.put(H1,H1);
		map.put(H2,H2);
		map.put(H3,H3);
		map.put(H4,H4);
		map.put(H5,H5);
		map.put(H6,H6);
		map.put(HEAD,HEAD);
		map.put(HR,HR);
		map.put(HTML,HTML);
		map.put(I,I);
		map.put(IFRAME,IFRAME);
		map.put(IMG,IMG);
		map.put(INPUT,INPUT);
		map.put(INS,INS);
		map.put(ISINDEX,ISINDEX);
		map.put(KBD,KBD);
		map.put(LABEL,LABEL);
		map.put(LEGEND,LEGEND);
		map.put(LI,LI);
		map.put(LINK,LINK);
		map.put(MAP,MAP);
		map.put(MENU,MENU);
		map.put(META,META);
		map.put(NOFRAMES,NOFRAMES);
		map.put(NOSCRIPT,NOSCRIPT);
		map.put(OBJECT,OBJECT);
		map.put(OL,OL);
		map.put(OPTGROUP,OPTGROUP);
		map.put(OPTION,OPTION);
		map.put(P,P);
		map.put(PARAM,PARAM);
		map.put(PRE,PRE);
		map.put(Q,Q);
		map.put(S,S);
		map.put(SAMP,SAMP);
		map.put(SCRIPT,SCRIPT);
		map.put(SELECT,SELECT);
		map.put(SMALL,SMALL);
		map.put(SPAN,SPAN);
		map.put(STRIKE,STRIKE);
		map.put(STRONG,STRONG);
		map.put(STYLE,STYLE);
		map.put(SUB,SUB);
		map.put(SUP,SUP);
		map.put(TABLE,TABLE);
		map.put(TBODY,TBODY);
		map.put(TD,TD);
		map.put(TEXTAREA,TEXTAREA);
		map.put(TFOOT,TFOOT);
		map.put(TH,TH);
		map.put(THEAD,THEAD);
		map.put(TITLE,TITLE);
		map.put(TR,TR);
		map.put(TT,TT);
		map.put(U,U);
		map.put(UL,UL);
		map.put(VAR,VAR);
		return map;
	}
}

