package com.suxia.cc.mybatis.base.utils;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description HTML操作工具类
 * @date 2020/4/22 10:35
 */
public class HtmlUtil {

    /**
     * 组装HTML用于word下载
     */
    public static String packWordHtml(String htmlBody) {

        StringBuffer buf = new StringBuffer();
        buf.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        buf.append("<html xmlns:v=\"urn:schemas-microsoft-com:vml\" ");
        buf.append(" xmlns:o=\"urn:schemas-microsoft-com:office:office\" ");
        buf.append(" xmlns:w=\"urn:schemas-microsoft-com:office:word\" ");
        buf.append(" xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\" ");
        buf.append(" xmlns=\"http://www.w3.org/TR/REC-html40\"> ");
        buf.append(" <head> ");
        buf.append(" <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /> ");
        buf.append(" <!--[if gte mso 9]><xml><w:WordDocument><w:View>Print</w:View><w:TrackMoves>false</w:TrackMoves><w:TrackFormatting/><w:ValidateAgainstSchemas/><w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid><w:IgnoreMixedContent>false</w:IgnoreMixedContent><w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText><w:DoNotPromoteQF/><w:LidThemeOther>EN-US</w:LidThemeOther><w:LidThemeAsian>ZH-CN</w:LidThemeAsian><w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript><w:Compatibility><w:BreakWrappedTables/><w:SnapToGridInCell/><w:WrapTextWithPunct/><w:UseAsianBreakRules/><w:DontGrowAutofit/><w:SplitPgBreakAndParaMark/><w:DontVertAlignCellWithSp/><w:DontBreakConstrainedForcedTables/><w:DontVertAlignInTxbx/><w:Word11KerningPairs/><w:CachedColBalance/><w:UseFELayout/></w:Compatibility><w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel><m:mathPr><m:mathFont m:val=\"Cambria Math\"/><m:brkBin m:val=\"before\"/><m:brkBinSub m:val=\"--\"/><m:smallFrac m:val=\"off\"/><m:dispDef/><m:lMargin m:val=\"0\"/> <m:rMargin m:val=\"0\"/><m:defJc m:val=\"centerGroup\"/><m:wrapIndent m:val=\"1440\"/><m:intLim m:val=\"subSup\"/><m:naryLim m:val=\"undOvr\"/></m:mathPr></w:WordDocument></xml><![endif]--> ");
        buf.append(" <style text=\"text.css\"> ");
        buf.append(" body{margin:8px;font-family:\"Microsoft YaHei\";font-size:16px;} ");
        buf.append(" </style> ");
        buf.append(" </head> ");
        buf.append(" <body> ");
        buf.append(htmlBody);
        buf.append(" </body> ");
        buf.append("</html>");

        return buf.toString();
    }

    /**
     * 组装HTML用于生成PDF
     */
    public static String packPdfHtml(String htmlBody) {

        StringBuffer buf = new StringBuffer();
        buf.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        buf.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        buf.append(" <head> ");
        buf.append(" <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /> ");
        buf.append(" <style text=\"text/css\"> ");
        buf.append(" body{margin:8px;font-family:\"Microsoft YaHei\";font-size:16px;} ");
        buf.append(" @page{ size : A4; margin:3.7cm 2.6cm 3.5cm 2.8cm;}");
        buf.append(" </style> ");
        buf.append(" </head> ");
        buf.append(" <body> ");
        buf.append(htmlBody);
        buf.append(" </body> ");
        buf.append("</html>");

        return buf.toString();
    }
}
