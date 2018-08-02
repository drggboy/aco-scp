package eu.andredick.tools;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;

public class ReportTools {

    JEditorPane jEditorPane;

    public ReportTools() {
        this.jEditorPane = new JEditorPane();
        initView();
    }

    public static String createTable(String[] titles, String[][] values) {

        String z_line = "";

        z_line += "<table class=\"table\"><thead><tr>";
        for (String title : titles) {
            z_line += "<th scope=\"col\">" + title + "</th>";
        }
        z_line += "</tr></thead>";
        z_line += "<tbody>";
        for (String[] row : values) {
            z_line += "<tr>";
            for (String cell : row) {
                z_line += "<td>" + cell + "</td>";
            }
            z_line += "</tr>";
        }

        z_line += "</tbody></table>";

        return z_line;
    }

    public void updateTable(String title, String subTitle, String htmlTable) {
        // create some simple html as a string
        String htmlString = "<html>\n"
                + "<body>\n"
                + "<h1>" + title + "</h1>\n"
                + "<h2>" + subTitle + "</h2>\n"
                + htmlTable
                + "</body>\n";
        jEditorPane.setText(htmlString);

    }

    public JEditorPane initView() {
        // create jeditorpane
        jEditorPane = new JEditorPane();

        // make it read-only
        jEditorPane.setEditable(false);

        // create a scrollpane; modify its attributes as desired
        JScrollPane scrollPane = new JScrollPane(jEditorPane);

        // add an html editor kit
        HTMLEditorKit kit = new HTMLEditorKit();
        jEditorPane.setEditorKit(kit);

        // add some styles to the html
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
        styleSheet.addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }");

        // create a document, set it on the jeditorpane, then add the html
        Document doc = kit.createDefaultDocument();
        jEditorPane.setDocument(doc);


        // now add it all to a frame
        JFrame j = new JFrame("HtmlEditorKit Test");
        j.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // make it easy to close the application
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // display the frame
        j.setSize(new Dimension(800, 200));

        // center the jframe, then make it visible
        j.setLocationRelativeTo(null);
        j.setVisible(true);

        return jEditorPane;
    }
}
