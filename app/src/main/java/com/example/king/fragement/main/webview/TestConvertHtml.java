package com.example.king.fragement.main.webview;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.king.fragement.R;
import com.example.king.fragement.main.BaseActivity;
import com.example.king.fragement.main.utils.Html2BitmapUtils;

/**
 * Created by Kings on 2016/6/7.
 */
public class TestConvertHtml extends BaseActivity {
    private String html;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        html = "<html xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" xmlns=\"http://www.w3.org/TR/REC-html40\"><head><meta http-equiv=Content-Type  content=\"text/html; charset=gb2312\" ><title></title><!--[if gte mso 9]><xml><w:WordDocument><w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel><w:DisplayHorizontalDrawingGridEvery>0</w:DisplayHorizontalDrawingGridEvery><w:DisplayVerticalDrawingGridEvery>2</w:DisplayVerticalDrawingGridEvery><w:DocumentKind>DocumentNotSpecified</w:DocumentKind><w:DrawingGridVerticalSpacing>7.8 磅</w:DrawingGridVerticalSpacing><w:PunctuationKerning></w:PunctuationKerning><w:View>Web</w:View><w:Compatibility><w:DontGrowAutofit/><w:BalanceSingleByteDoubleByteWidth/><w:DoNotExpandShiftReturn/><w:UseFELayout/></w:Compatibility><w:Zoom>0</w:Zoom></w:WordDocument></xml><![endif]--><!--[if gte mso 9]><xml><w:LatentStyles DefLockedState=\"false\"  DefUnhideWhenUsed=\"true\"  DefSemiHidden=\"true\"  DefQFormat=\"false\"  DefPriority=\"99\"  LatentStyleCount=\"260\" >\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"0\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  QFormat=\"true\"  Name=\"Normal\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"9\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  QFormat=\"true\"  Name=\"heading 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"9\"  SemiHidden=\"false\"  QFormat=\"true\"  Name=\"heading 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"9\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  QFormat=\"true\"  Name=\"heading 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"9\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  QFormat=\"true\"  Name=\"heading 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"9\"  SemiHidden=\"false\"  QFormat=\"true\"  Name=\"heading 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"9\"  SemiHidden=\"false\"  QFormat=\"true\"  Name=\"heading 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"9\"  SemiHidden=\"false\"  QFormat=\"true\"  Name=\"heading 7\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"9\"  SemiHidden=\"false\"  QFormat=\"true\"  Name=\"heading 8\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"9\"  SemiHidden=\"false\"  QFormat=\"true\"  Name=\"heading 9\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"index 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"index 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"index 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"index 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"index 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"index 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"index 7\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"index 8\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"index 9\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"39\"  SemiHidden=\"false\"  Name=\"toc 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"39\"  SemiHidden=\"false\"  Name=\"toc 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"39\"  SemiHidden=\"false\"  Name=\"toc 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"39\"  SemiHidden=\"false\"  Name=\"toc 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"39\"  SemiHidden=\"false\"  Name=\"toc 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"39\"  SemiHidden=\"false\"  Name=\"toc 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"39\"  SemiHidden=\"false\"  Name=\"toc 7\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"39\"  SemiHidden=\"false\"  Name=\"toc 8\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"39\"  SemiHidden=\"false\"  Name=\"toc 9\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Normal Indent\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"footnote text\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"annotation text\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"header\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  QFormat=\"true\"  Name=\"footer\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"index heading\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"35\"  SemiHidden=\"false\"  QFormat=\"true\"  Name=\"caption\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"table of figures\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"envelope address\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"envelope return\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"footnote reference\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"annotation reference\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"line number\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"page number\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"endnote reference\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"endnote text\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"table of authorities\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"macro\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"toa heading\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List Bullet\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List Number\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List Bullet 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List Bullet 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List Bullet 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List Bullet 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List Number 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List Number 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List Number 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List Number 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"10\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  QFormat=\"true\"  Name=\"Title\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Closing\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Signature\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"1\"  SemiHidden=\"false\"  Name=\"Default Paragraph Font\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Body Text\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Body Text Indent\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List Continue\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List Continue 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List Continue 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List Continue 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"List Continue 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Message Header\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"11\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  QFormat=\"true\"  Name=\"Subtitle\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Salutation\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Date\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Body Text First Indent\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Body Text First Indent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Note Heading\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Body Text 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Body Text 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Body Text Indent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Body Text Indent 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Block Text\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Hyperlink\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"FollowedHyperlink\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"22\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  QFormat=\"true\"  Name=\"Strong\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"20\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  QFormat=\"true\"  Name=\"Emphasis\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Document Map\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Plain Text\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"E-mail Signature\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Normal (Web)\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"HTML Acronym\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"HTML Address\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"HTML Cite\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"HTML Code\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"HTML Definition\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"HTML Keyboard\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"HTML Preformatted\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"HTML Sample\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"HTML Typewriter\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"HTML Variable\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  QFormat=\"true\"  Name=\"Normal Table\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"annotation subject\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"No List\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Simple 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Simple 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Simple 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Classic 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Classic 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Classic 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Classic 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Colorful 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Colorful 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Colorful 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Columns 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Columns 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Columns 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Columns 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Columns 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Grid 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Grid 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Grid 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Grid 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Grid 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Grid 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Grid 7\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Grid 8\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table List 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table List 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table List 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table List 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table List 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table List 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table List 7\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table List 8\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table 3D effects 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table 3D effects 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table 3D effects 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Contemporary\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Elegant\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Professional\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Subtle 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Subtle 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Web 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Web 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Web 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Balloon Text\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"59\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Table Grid\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"99\"  SemiHidden=\"false\"  Name=\"Table Theme\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"60\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light Shading\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"61\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light List\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"62\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light Grid\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"63\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Shading 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"64\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Shading 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"65\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium List 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"66\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium List 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"67\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"68\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"69\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"70\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Dark List\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"71\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful Shading\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"72\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful List\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"73\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful Grid\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"60\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light Shading Accent 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"61\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light List Accent 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"62\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light Grid Accent 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"63\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Shading 1 Accent 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"64\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Shading 2 Accent 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"65\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium List 1 Accent 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"66\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium List 2 Accent 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"67\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 1 Accent 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"68\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 2 Accent 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"69\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 3 Accent 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"70\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Dark List Accent 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"71\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful Shading Accent 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"72\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful List Accent 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"73\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful Grid Accent 1\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"60\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light Shading Accent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"61\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light List Accent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"62\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light Grid Accent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"63\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Shading 1 Accent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"64\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Shading 2 Accent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"65\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium List 1 Accent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"66\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium List 2 Accent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"67\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 1 Accent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"68\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 2 Accent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"69\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 3 Accent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"70\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Dark List Accent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"71\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful Shading Accent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"72\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful List Accent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"73\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful Grid Accent 2\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"60\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light Shading Accent 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"61\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light List Accent 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"62\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light Grid Accent 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"63\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Shading 1 Accent 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"64\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Shading 2 Accent 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"65\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium List 1 Accent 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"66\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium List 2 Accent 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"67\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 1 Accent 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"68\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 2 Accent 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"69\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 3 Accent 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"70\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Dark List Accent 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"71\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful Shading Accent 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"72\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful List Accent 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"73\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful Grid Accent 3\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"60\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light Shading Accent 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"61\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light List Accent 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"62\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light Grid Accent 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"63\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Shading 1 Accent 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"64\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Shading 2 Accent 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"65\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium List 1 Accent 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"66\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium List 2 Accent 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"67\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 1 Accent 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"68\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 2 Accent 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"69\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 3 Accent 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"70\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Dark List Accent 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"71\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful Shading Accent 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"72\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful List Accent 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"73\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful Grid Accent 4\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"60\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light Shading Accent 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"61\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light List Accent 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"62\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light Grid Accent 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"63\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Shading 1 Accent 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"64\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Shading 2 Accent 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"65\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium List 1 Accent 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"66\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium List 2 Accent 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"67\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 1 Accent 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"68\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 2 Accent 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"69\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 3 Accent 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"70\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Dark List Accent 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"71\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful Shading Accent 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"72\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful List Accent 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"73\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful Grid Accent 5\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"60\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light Shading Accent 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"61\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light List Accent 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"62\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Light Grid Accent 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"63\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Shading 1 Accent 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"64\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Shading 2 Accent 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"65\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium List 1 Accent 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"66\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium List 2 Accent 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"67\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 1 Accent 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"68\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 2 Accent 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"69\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Medium Grid 3 Accent 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"70\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Dark List Accent 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"71\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful Shading Accent 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"72\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful List Accent 6\" ></w:LsdException>\n" +
                "<w:LsdException Locked=\"false\"  Priority=\"73\"  SemiHidden=\"false\"  UnhideWhenUsed=\"false\"  Name=\"Colorful Grid Accent 6\" ></w:LsdException>\n" +
                "</w:LatentStyles></xml><![endif]--><style>\n" +
                "@font-face{\n" +
                "font-family:\"Times New Roman\";\n" +
                "}\n" +
                "\n" +
                "@font-face{\n" +
                "font-family:\"宋体\";\n" +
                "}\n" +
                "\n" +
                "@font-face{\n" +
                "font-family:\"Calibri\";\n" +
                "}\n" +
                "\n" +
                "@font-face{\n" +
                "font-family:\"Wingdings\";\n" +
                "}\n" +
                "\n" +
                "@list l0:level1{\n" +
                "mso-level-number-format:decimal;\n" +
                "mso-level-suffix:tab;\n" +
                "mso-level-text:\"%1.\";\n" +
                "mso-level-tab-stop:39.0000pt;\n" +
                "mso-level-number-position:left;\n" +
                "margin-left:39.0000pt;text-indent:-18.0000pt;font-family:'Times New Roman';}\n" +
                "\n" +
                "@list l1:level1{\n" +
                "mso-level-number-format:bullet;\n" +
                "mso-level-suffix:tab;\n" +
                "mso-level-text:\\F06C;\n" +
                "mso-level-tab-stop:81.0000pt;\n" +
                "mso-level-number-position:left;\n" +
                "margin-left:81.0000pt;text-indent:-18.0000pt;font-family:Wingdings;}\n" +
                "\n" +
                "@list l2:level1{\n" +
                "mso-level-number-format:decimal;\n" +
                "mso-level-suffix:tab;\n" +
                "mso-level-text:\"%1.\";\n" +
                "mso-level-tab-stop:18.0000pt;\n" +
                "mso-level-number-position:left;\n" +
                "margin-left:18.0000pt;text-indent:-18.0000pt;font-family:'Times New Roman';}\n" +
                "\n" +
                "@list l3:level1{\n" +
                "mso-level-number-format:bullet;\n" +
                "mso-level-suffix:tab;\n" +
                "mso-level-text:\\F06C;\n" +
                "mso-level-tab-stop:18.0000pt;\n" +
                "mso-level-number-position:left;\n" +
                "margin-left:18.0000pt;text-indent:-18.0000pt;font-family:Wingdings;}\n" +
                "\n" +
                "@list l4:level1{\n" +
                "mso-level-number-format:bullet;\n" +
                "mso-level-suffix:tab;\n" +
                "mso-level-text:\\F06C;\n" +
                "mso-level-tab-stop:60.0000pt;\n" +
                "mso-level-number-position:left;\n" +
                "margin-left:60.0000pt;text-indent:-18.0000pt;font-family:Wingdings;}\n" +
                "\n" +
                "@list l5:level1{\n" +
                "mso-level-number-format:decimal;\n" +
                "mso-level-suffix:tab;\n" +
                "mso-level-text:\"%1.\";\n" +
                "mso-level-tab-stop:60.0000pt;\n" +
                "mso-level-number-position:left;\n" +
                "margin-left:60.0000pt;text-indent:-18.0000pt;font-family:'Times New Roman';}\n" +
                "\n" +
                "@list l6:level1{\n" +
                "mso-level-number-format:bullet;\n" +
                "mso-level-suffix:tab;\n" +
                "mso-level-text:\\F06C;\n" +
                "mso-level-tab-stop:39.0000pt;\n" +
                "mso-level-number-position:left;\n" +
                "margin-left:39.0000pt;text-indent:-18.0000pt;font-family:Wingdings;}\n" +
                "\n" +
                "@list l7:level1{\n" +
                "mso-level-number-format:bullet;\n" +
                "mso-level-suffix:tab;\n" +
                "mso-level-text:\\F06C;\n" +
                "mso-level-tab-stop:102.0000pt;\n" +
                "mso-level-number-position:left;\n" +
                "margin-left:102.0000pt;text-indent:-18.0000pt;font-family:Wingdings;}\n" +
                "\n" +
                "@list l8:level1{\n" +
                "mso-level-number-format:decimal;\n" +
                "mso-level-suffix:tab;\n" +
                "mso-level-text:\"%1.\";\n" +
                "mso-level-tab-stop:81.0000pt;\n" +
                "mso-level-number-position:left;\n" +
                "margin-left:81.0000pt;text-indent:-18.0000pt;font-family:'Times New Roman';}\n" +
                "\n" +
                "@list l9:level1{\n" +
                "mso-level-number-format:decimal;\n" +
                "mso-level-suffix:tab;\n" +
                "mso-level-text:\"%1.\";\n" +
                "mso-level-tab-stop:102.0000pt;\n" +
                "mso-level-number-position:left;\n" +
                "margin-left:102.0000pt;text-indent:-18.0000pt;font-family:'Times New Roman';}\n" +
                "\n" +
                "p.MsoNormal{\n" +
                "mso-style-name:正文;\n" +
                "mso-style-parent:\"\";\n" +
                "margin:0pt;\n" +
                "mso-pagination:none;\n" +
                "text-align:justify;\n" +
                "text-justify:inter-ideograph;\n" +
                "font-family:Calibri;\n" +
                "mso-fareast-font-family:宋体;\n" +
                "mso-bidi-font-family:'Times New Roman';\n" +
                "font-size:10.5000pt;\n" +
                "mso-font-kerning:1.0000pt;\n" +
                "margin-bottom: 10px;\n" +
                "line-height:24px;\n" +
                "}\n" +
                "\n" +
                "h3{\n" +
                "mso-style-name:\"标题 3\";\n" +
                "mso-style-next:正文;\n" +
                "margin-top:5.0000pt;\n" +
                "margin-bottom:5.0000pt;\n" +
                "mso-margin-top-alt:auto;\n" +
                "mso-margin-bottom-alt:auto;\n" +
                "mso-pagination:widow-orphan;\n" +
                "text-align:left;\n" +
                "mso-outline-level:3;\n" +
                "font-family:宋体;\n" +
                "font-weight:bold;\n" +
                "font-size:13.5000pt;\n" +
                "}\n" +
                "\n" +
                "h4{\n" +
                "mso-style-name:\"标题 4\";\n" +
                "mso-style-next:正文;\n" +
                "margin-top:5.0000pt;\n" +
                "margin-bottom:5.0000pt;\n" +
                "mso-margin-top-alt:auto;\n" +
                "mso-margin-bottom-alt:auto;\n" +
                "mso-pagination:widow-orphan;\n" +
                "text-align:left;\n" +
                "mso-outline-level:4;\n" +
                "font-family:宋体;\n" +
                "font-weight:bold;\n" +
                "font-size:12.0000pt;\n" +
                "}\n" +
                "\n" +
                "span.10{\n" +
                "font-family:'Times New Roman';\n" +
                "}\n" +
                "\n" +
                "span.15{\n" +
                "font-family:'Times New Roman';\n" +
                "font-size:9.0000pt;\n" +
                "}\n" +
                "\n" +
                "span.16{\n" +
                "font-family:宋体;\n" +
                "font-weight:bold;\n" +
                "font-size:12.0000pt;\n" +
                "}\n" +
                "\n" +
                "span.17{\n" +
                "font-family:宋体;\n" +
                "font-weight:bold;\n" +
                "font-size:13.5000pt;\n" +
                "}\n" +
                "\n" +
                "span.18{\n" +
                "font-family:'Times New Roman';\n" +
                "}\n" +
                "\n" +
                "span.19{\n" +
                "font-family:'Times New Roman';\n" +
                "font-size:9.0000pt;\n" +
                "}\n" +
                "\n" +
                "p.MsoHeader{\n" +
                "mso-style-name:页眉;\n" +
                "mso-style-noshow:yes;\n" +
                "margin:0pt;\n" +
                "margin-bottom:.0001pt;\n" +
                "border-bottom:0.7500pt solid windowtext;\n" +
                "mso-border-bottom-alt:0.7500pt solid windowtext;\n" +
                "padding:0pt 0pt 1pt 0pt ;\n" +
                "layout-grid-mode:char;\n" +
                "mso-pagination:none;\n" +
                "text-align:center;\n" +
                "font-family:Calibri;\n" +
                "mso-fareast-font-family:宋体;\n" +
                "mso-bidi-font-family:'Times New Roman';\n" +
                "font-size:9.0000pt;\n" +
                "mso-font-kerning:1.0000pt;\n" +
                "}\n" +
                "\n" +
                "p.MsoFooter{\n" +
                "mso-style-name:页脚;\n" +
                "mso-style-noshow:yes;\n" +
                "margin:0pt;\n" +
                "margin-bottom:.0001pt;\n" +
                "layout-grid-mode:char;\n" +
                "mso-pagination:none;\n" +
                "text-align:left;\n" +
                "font-family:Calibri;\n" +
                "mso-fareast-font-family:宋体;\n" +
                "mso-bidi-font-family:'Times New Roman';\n" +
                "font-size:9.0000pt;\n" +
                "mso-font-kerning:1.0000pt;\n" +
                "}\n" +
                "\n" +
                "span.msoIns{\n" +
                "mso-style-type:export-only;\n" +
                "mso-style-name:\"\";\n" +
                "text-decoration:underline;\n" +
                "text-underline:single;\n" +
                "color:blue;\n" +
                "}\n" +
                "\n" +
                "span.msoDel{\n" +
                "mso-style-type:export-only;\n" +
                "mso-style-name:\"\";\n" +
                "text-decoration:line-through;\n" +
                "color:red;\n" +
                "}\n" +
                "\n" +
                "table.MsoNormalTable{\n" +
                "mso-style-name:普通表格;\n" +
                "mso-style-parent:\"\";\n" +
                "mso-style-noshow:yes;\n" +
                "mso-tstyle-rowband-size:0;\n" +
                "mso-tstyle-colband-size:0;\n" +
                "mso-padding-alt:0.0000pt 5.4000pt 0.0000pt 5.4000pt;\n" +
                "mso-para-margin:0pt;\n" +
                "mso-para-margin-bottom:.0001pt;\n" +
                "mso-pagination:widow-orphan;\n" +
                "font-family:'Times New Roman';\n" +
                "font-size:10.0000pt;\n" +
                "mso-ansi-language:#0400;\n" +
                "mso-fareast-language:#0400;\n" +
                "mso-bidi-language:#0400;\n" +
                "}\n" +
                "@page{mso-page-border-surround-header:no;\n" +
                "\tmso-page-border-surround-footer:no;}@page Section0{\n" +
                "margin-top:72.0000pt;\n" +
                "margin-bottom:72.0000pt;\n" +
                "margin-left:90.0000pt;\n" +
                "margin-right:90.0000pt;\n" +
                "size:595.3000pt 841.9000pt;\n" +
                "layout-grid:15.6000pt;\n" +
                "}\n" +
                "\n" +
                "div.Section0{page:Section0;}</style></head>\n" +
                "<body style=\"tab-interval:21pt; text-justify-trim:punctuation; padding:10px;\" ><!--StartFragment--><div class=\"Section0\"  style=\"layout-grid:15.6000pt; \" ><p class=MsoNormal  style=\"margin-top:3.7500pt;margin-bottom:3.7500pt;mso-pagination:widow-orphan;text-align:left;mso-outline-level:4;line-height:15.0000pt;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-weight:bold;font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >地推高手APP用户注册协议</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-weight:bold;font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"margin-top:3.7500pt;margin-bottom:3.7500pt;mso-pagination:widow-orphan;text-align:left;mso-outline-level:4;line-height:15.0000pt;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-weight:bold;font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >一、总则</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >1.1&nbsp;</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >地推高手APP</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >的所有权</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >、</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >运营权</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >及解释权</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >归</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >广州云移信息科技</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >有限公司所有。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >（以下简称云移科技）</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >&#160;</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >1.2&nbsp;用户在注册之前，应当仔细阅读本协议，并同意遵守本协议后方可成为注册用户。一旦注册成功，则与</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >云移科技</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >之间自动形成协议关系，用户应当受本协议的约束。&#160;</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >1.3&nbsp;本协议则</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >会随着地推高手APP的功能更新，做</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >随时更新</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >；</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >用户应当及时关注</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >消息通知。对此，云移科技不承担</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >通知义务</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >；地推高手APP内所所有</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >通知、公告、声明或其它类似内容是本协议的一部分。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >二、服务内容</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >2.1</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >地推高手APP</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >仅提供</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >与掌贝业务相关的服务</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >，</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >包括但不限于支撑掌贝商户进件、活动信息推送、商机推送、商机拓展、机具销售等。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >除此之外与相关</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >掌贝业务</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >服务</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >无关的事项、云移科技不承担任何责任。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >2.2</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >&nbsp;云移科技对超过6个月未登录使用的帐号，保留关闭的权利。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >2.3&nbsp;云移科技有权于任何时间暂时或永久修改或终止本服务（或其任何部分），而无论其通知与否，对用户和任何第三人均无需承担任何责任。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >2.4地推高手APP</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >的具体内容由</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >云移科技</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >根据实际情况提供</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >；并对所提供之服务拥有最终解释权。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >三、用户帐号</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >3.1</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >&nbsp;经注册系统完成注册程序并通过</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >身份用户认证的</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >用户即成为</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >地推高手APP的</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >用户，可以获得</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >地推高手APP</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >的</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >使用</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >权限</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >3.2</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >&nbsp;用户只能按照注册要求使用</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >手机号码</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >注册</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >、注册完成之后进行相关认证和所属代理商绑定</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >。用户有义务保证密码和帐号的安全，用户利用该密码和帐号所进行的一切活动引起的任何损失或损害，由用户自行承担全部责任，</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >云移科技</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >不承担任何责任。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >3.3</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >如用户发现帐号遭到未授权的使用或发生其他任何安全问题，应立即修改帐号密码并妥善保管，如有必要，请通知</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >云移科技管理员</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >四、&nbsp;注册信息和隐私保护</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >4.1地推高手APP的帐号所有权归云移科技所有，用户完成注册申请手续后，获得地推高手APP帐号的使用权。用户应提供及时、详尽及准确的个人资料，符合及时、详尽准确的要求。所有原始键入的资料将作为注册资料。如果因用户注册信息不真实而引起的问题及其产生的后果，由用户自行全责承担。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >4.2&nbsp;用户不得将其帐号、密码转让或出借予他人使用。如发现其帐号遭他人非法使用，应立即通知云移科技管理员。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >4.3&nbsp;云移科技承诺不对外公开或向第三方提供单个用户的注册资料，除非：</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >&#8226;&nbsp;事先获得用户的明确授权；</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >&#8226;&nbsp;只有透露你的个人资料，才能提供你所要求的产品和服务；</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >&#8226;&nbsp;根据有关的法律法规要求；</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >&#8226;&nbsp;按照相关政府主管部门的要求；</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >&#8226;&nbsp;为维护云移科技的合法权益。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >4.4&nbsp;因不可抗力或者其他意外事件，使得本协议的履行不可能、不必要或者无意义的，双方均不承担责任。本协议所称之不可抗力意指不能预见、不能避免并不能克服的客观情况，包括但不限于战争、台风、水灾、火灾、雷击或地震、罢工、暴动、法定疾病、黑客攻击、网络病毒、电信部门技术管制、政府行为或任何其它自然或人为造成的灾难等客观情况。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >4.5&nbsp;本条款及其修订本的有效性、履行和与本条款及其修订本效力有关的所有事宜，将受中华人民共和国法律约束，任何争议仅适用中华人民共和国法律。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >4.6&nbsp;因本条款所引起的用户与本公司的任何纠纷或争议，首先应友好协商解决，协商不成的，用户在此同意将纠纷或争议提交本公司住所地有管辖权的人民法院诉讼解决。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >五、隐私保护</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >5.</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >1</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >&nbsp;</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >用户在使用地推高手APP期间，有责任保证商户的一切资料不被盗用、泄露以及其他用途的责任，由此对云移科技及合作伙伴造成的经济损失，由账户本人全责承担。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >5.2&nbsp;用户不得对地推高手APP任何部分及上传的商户资料，通过任何方式进行复制、拷贝、出售、转售或用于任何其它商业目的。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >5.3使用地推高手APP功能在未经授权时，不得公开、透露、出售商户信息，有此造成的对云移科技的经济损失以及各类经济纠纷，由账户本人全责承担。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p>&nbsp;</o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >六</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >、附则</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >6</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >.1&nbsp;本协议的订立、执行和解释及争议的解决均应适用中华人民共和国法律。&#160;</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><br></span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >6</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >.2&nbsp;如本协议中的任何条款无论因何种原因完全或部分无效或不具有执行力，本协议的其余条款仍应有效并且有约束力。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><br></span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >6</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >.3&nbsp;本协议解释权及修订权归</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >广州云移信息科技</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >有限公司所有。</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >6.4出现下列情况之一的，云移科技有权在不经事先通知的情况下，立即终止您使地推高手APP的权利，而无需承担任何责任：&nbsp;&nbsp;</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >（1）您违反本协议有关保证、同意、承诺条款的约定；&nbsp;</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >（2）您违反云移科技对于地推高手APP服务条款、协议、规则、通告等相关规定，擅用地推高手谋取他利等行为；</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p><p class=MsoNormal  style=\"mso-pagination:widow-orphan;text-align:left;\" ><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" >（3）您与从属的代理商接触劳动合同后（离职），云移科技保有权力在规定时间内中止您的账户使用权限。&nbsp;</span><span style=\"mso-spacerun:'yes';font-family:宋体;color:rgb(51,51,51);font-size:10.5000pt;mso-font-kerning:0.0000pt;\" ><o:p></o:p></span></p></div><!--EndFragment--></body></html>";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.html_bitmap);
        ImageView img = (ImageView) findViewById(R.id.html_img);
        WebView wv = (WebView) findViewById(R.id.html_webview);
        Html2BitmapUtils.convert(wv,html,img);
    }
}
