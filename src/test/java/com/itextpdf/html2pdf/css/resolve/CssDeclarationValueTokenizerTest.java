/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2018 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.html2pdf.css.parse.CssDeclarationValueTokenizer;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Arrays;
import java.util.List;

@Category(UnitTest.class)
public class CssDeclarationValueTokenizerTest extends ExtendedITextTest {

    @Test
    public void functionTest01() {
        runTest("func(param)", Arrays.asList("func(param)"), Arrays.asList(CssDeclarationValueTokenizer.TokenType.FUNCTION));
    }

    @Test
    public void functionTest02() {
        runTest("func(param1, param2)", Arrays.asList("func(param1, param2)"), Arrays.asList(CssDeclarationValueTokenizer.TokenType.FUNCTION));
    }

    @Test
    public void functionTest03() {
        runTest("func(param,'param)',\"param))\")", Arrays.asList("func(param,'param)',\"param))\")"), Arrays.asList(CssDeclarationValueTokenizer.TokenType.FUNCTION));
    }

    @Test
    public void functionTest04() {
        runTest("func(param, innerFunc())", Arrays.asList("func(param, innerFunc())"), Arrays.asList(CssDeclarationValueTokenizer.TokenType.FUNCTION));
    }

    @Test
    public void functionTest05() {
        runTest(") )) function()", Arrays.asList(")", "))", "function()"), Arrays.asList(CssDeclarationValueTokenizer.TokenType.UNKNOWN, CssDeclarationValueTokenizer.TokenType.UNKNOWN, CssDeclarationValueTokenizer.TokenType.FUNCTION));
    }

    @Test
    public void functionTest06() {
        runTest("a('x'), b('x')", Arrays.asList("a('x')", ",", "b('x')"), Arrays.asList(CssDeclarationValueTokenizer.TokenType.FUNCTION, CssDeclarationValueTokenizer.TokenType.COMMA, CssDeclarationValueTokenizer.TokenType.FUNCTION));
    }

    private void runTest(String src, List<String> tokenValues, List<CssDeclarationValueTokenizer.TokenType> tokenTypes) {
        CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(src);
        CssDeclarationValueTokenizer.Token token = null;
        Assert.assertTrue("Value and type arrays size should be equal", tokenValues.size() == tokenTypes.size());
        int index = 0;
        while ((token = tokenizer.getNextValidToken()) != null) {
            Assert.assertEquals(tokenValues.get(index), token.getValue());
            Assert.assertEquals(tokenTypes.get(index), token.getType());
            ++index;
        }
        Assert.assertTrue(index == tokenValues.size());
    }
}
