package eu.acls.nutslotspacer.swingapp;

import eu.acls.nutslotspacer.Calculator;
import eu.acls.nutslotspacer.Result;
import eu.acls.nutslotspacer.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;


public class NutSlotSpacerApp extends JFrame {

  private JTextField tfStringDiameters;
  private JTextField tfEdgeSpacingTreble;
  private JTextField tfEdgeSpacingBass;
  private JTextField tfNutWidth;
  private JButton btnManual;
  private JButton btnAbout;
  private JButton btnCalculate;
  private JButton btnDonate;

  private ButtonGroup buttonGroupInchMm;
  private ButtonGroup buttonGroupEdgeCenter;

  private JRadioButton rbInches;
  private JRadioButton rbMillimeters;
  private JRadioButton rbEdge;
  private JRadioButton rbCenter;

  private JPanel mainPanel;
  private JPanel aboutPanel;
  private JPanel inputPanel;
  private JPanel outputPanel;
  private JEditorPane jTextPaneLeft;
  private JEditorPane jTextPaneRight;

  private Color backgroundColor = new Color(0xeeeeee);

  public NutSlotSpacerApp() {

    aboutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    outputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    mainPanel.setBackground(backgroundColor);
    inputPanel.setBackground(backgroundColor);
    outputPanel.setBackground(backgroundColor);
    aboutPanel.setBackground(backgroundColor);

    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    outputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    aboutPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    mainPanel.add(aboutPanel);
    mainPanel.add(inputPanel);
    mainPanel.add(outputPanel);
    setContentPane(mainPanel);

    initializeSwingComponents();

    setTitle("Nut Slot Spacer");
    setBounds(100, 100, 800, 650);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);

    Calculator calculator = new Calculator();

    btnCalculate.addActionListener(actionEvent -> {

      calculator.setEdgeSpacingBass(Double.parseDouble(tfEdgeSpacingBass.getText()));
      calculator.setEdgeSpacingTreble(Double.parseDouble(tfEdgeSpacingTreble.getText()));
      calculator.setNutWidth(Double.parseDouble(tfNutWidth.getText()));
      calculator.setUseStringCenterToFingerboardEdge(rbCenter.isSelected());
      calculator.setUseInchesAsInput(rbInches.isSelected());

      String text = tfStringDiameters.getText();
      String[] stringDiameters = text.split(",");

      for (String diameter : stringDiameters) {

        calculator.addStringDiameter(Integer.parseInt(diameter.trim()));
      }

      Result result = calculator.calculate();
      calculator.getStringDiameters().clear();

      String output = getTextFromFile("/calculation-template.html");
      jTextPaneRight.setText(output);

      try {
        insertCalculationHtml(result);
      } catch (IOException | BadLocationException e) {
        e.printStackTrace();
      }
    });

    btnManual.addActionListener(actionEvent -> {
      String manual = getTextFromFile("/manual.html");
      jTextPaneRight.setText(manual);
    });

    btnDonate.addActionListener(actionEvent -> {
      try {
        URI uri = new URI("https://github.com/sponsors/aclseelen");
        openWebpage(uri);
      } catch (URISyntaxException | IOException e) {
        e.printStackTrace();
      }
    });

    btnAbout.addActionListener(actionEvent -> {
      try {
        URI uri = new URI("https://github.com/aclseelen/nut-slot-spacer#readme");
        openWebpage(uri);
      } catch (URISyntaxException | IOException e) {
        e.printStackTrace();
      }
    });

    btnManual.doClick();
  }

  public static void main(String[] args) {

    new NutSlotSpacerApp();
  }

  private void insertCalculationHtml(Result result) throws IOException, BadLocationException {
    HTMLDocument document = (HTMLDocument) jTextPaneRight.getDocument();

    Element tableBassSide = document.getElement("table-bass-side");
    Element tableTrebleSide = document.getElement("table-treble-side");
    Element stringSpacingMm = document.getElement("string-spacing-mm");
    Element stringSpacingInches = document.getElement("string-spacing-inches");
    Element nStrings = document.getElement("n-strings");
    document.setInnerHTML(tableBassSide, convertResultToHtmlTableContentString(result.getStringCenterLinesFromBassEdge()));
    document.setInnerHTML(tableTrebleSide, convertResultToHtmlTableContentString(result.getStringCenterLinesFromTrebleEdge()));
    document.setInnerHTML(stringSpacingMm, String.valueOf(result.getStringSpacing()));
    document.setInnerHTML(stringSpacingInches,
      String.valueOf(Util.truncateDecimal(Util.millimetersToInches(result.getStringSpacing()), 5)));
    document.setInnerHTML(nStrings, String.valueOf(result.getStringCenterLinesFromBassEdge().size()));
  }

  private String convertResultToHtmlTableContentString(List<Double> stringCenterLinesFromBassEdge) {

    StringBuilder sb = new StringBuilder();

    sb.append("<tr>")
      .append("<th>").append("Millimeters").append("</th>")
      .append("<th>").append("Inches").append("</th>")
      .append("</tr>")
      .append("\n");

    for (double centerLine : stringCenterLinesFromBassEdge) {

      sb.append("<tr>")
        .append("<td>").append(Util.truncateDecimal(centerLine, 5)).append("</td>")
        .append("<td>").append(Util.truncateDecimal(Util.millimetersToInches(centerLine), 5)).append("</td>")
        .append("</tr>");
    }

    return sb.toString();
  }

  private String getTextFromFile(String path) {
    InputStream is = getClass().getResourceAsStream(path);

    if (is == null) {
      return "";
    }

    return new BufferedReader(new InputStreamReader(is,
      StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
  }

  private String getResultString(Result result) {

    StringBuilder formattedOutput = new StringBuilder();
    formattedOutput.append("Spacing between string edges:\n\n")
      .append("Millimeters\tInches\n")
      .append("-------------------------------------------\n")
      .append(Util.truncateDecimal(result.getStringSpacing(), 5)).append("\t")
      .append(Util.truncateDecimal(Util.millimetersToInches(result.getStringSpacing()), 5))
      .append("\"");

    formattedOutput.append("\n\n");
    formattedOutput.append("Number of strings:\t").append(result.getStringCenterLinesFromBassEdge().size());
    formattedOutput.append("\n");

    formattedOutput.append("\n");
    formattedOutput.append("Distances from bass edge:\n\n");
    formattedOutput.append("Millimeters\tInches\n");
    formattedOutput.append("-------------------------------------------\n");

    for (double centerLine : result.getStringCenterLinesFromBassEdge()) {
      formattedOutput
        .append(Util.truncateDecimal(centerLine, 5)).append("\t")
        .append(Util.truncateDecimal(Util.millimetersToInches(centerLine), 5)).append("\"")
        .append("\n");
    }

    formattedOutput.append("\n");
    formattedOutput.append("Distances from treble edge:\n\n");
    formattedOutput.append("Millimeters\tInches\n");
    formattedOutput.append("-------------------------------------------\n");

    for (double centerLine : result.getStringCenterLinesFromTrebleEdge()) {
      formattedOutput
        .append(Util.truncateDecimal(centerLine, 5)).append("\t")
        .append(Util.truncateDecimal(Util.millimetersToInches(centerLine), 5)).append("\"")
        .append("\n");
    }

    return formattedOutput.toString();
  }

  private boolean openWebpage(URI uri) throws IOException {
    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
      desktop.browse(uri);
      return true;
    }
    return false;
  }

  private void initializeSwingComponents() {

    Dimension inputPanelDimension = new Dimension(240, 560);
    Dimension outputPanelDimension = new Dimension(240, 560);
    Dimension aboutPanelDimension = new Dimension(240, 560);

    String description = getTextFromFile("/about.html");

    jTextPaneLeft = new JTextPane();
    jTextPaneLeft.setDocument(new HTMLDocument());
    jTextPaneLeft.setContentType("text/html");
    jTextPaneLeft.setText(description);
    jTextPaneLeft.addHyperlinkListener(hyperlinkEvent -> {

      if (!hyperlinkEvent.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
        return;
      }
      try {
        openWebpage(hyperlinkEvent.getURL().toURI());
      } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
      }
    });
    jTextPaneLeft.setBackground(backgroundColor);
    jTextPaneLeft.setPreferredSize(new Dimension(220, 350));
    jTextPaneLeft.setEditable(false);
    aboutPanel.add(jTextPaneLeft);

    btnAbout = new JButton("About");
    aboutPanel.add(btnAbout);
    btnDonate = new JButton("Donate");
    aboutPanel.add(btnDonate);

    aboutPanel.setPreferredSize(aboutPanelDimension);
    inputPanel.setPreferredSize(inputPanelDimension);

    rbMillimeters = new JRadioButton("Millimeters");
    rbInches = new JRadioButton("Inches");
    inputPanel.add(rbMillimeters);
    inputPanel.add(rbInches);
    buttonGroupInchMm = new ButtonGroup();
    buttonGroupInchMm.add(rbMillimeters);
    buttonGroupInchMm.add(rbInches);
    rbMillimeters.doClick();

    btnManual = new JButton();
    btnManual.setText("Click to open manual");
    inputPanel.add(btnManual);

    JLabel lblNutWidth = new JLabel("Nut width");
    inputPanel.add(lblNutWidth);

    tfNutWidth = new JTextField();
    tfNutWidth.setColumns(18);
    inputPanel.add(tfNutWidth);

    JLabel lblEdgeSpacingBass = new JLabel("Edge spacing bass side");
    inputPanel.add(lblEdgeSpacingBass);

    tfEdgeSpacingBass = new JTextField();
    tfEdgeSpacingBass.setColumns(18);
    inputPanel.add(tfEdgeSpacingBass);

    JLabel lblEdgeSpacingTreble = new JLabel("Edge spacing treble side");
    inputPanel.add(lblEdgeSpacingTreble);

    tfEdgeSpacingTreble = new JTextField();
    tfEdgeSpacingTreble.setColumns(18);
    inputPanel.add(tfEdgeSpacingTreble);

    JLabel lblStringDiameters = new JLabel("String diameters");
    inputPanel.add(lblStringDiameters);

    tfStringDiameters = new JTextField();
    tfStringDiameters.setColumns(18);
    inputPanel.add(tfStringDiameters);

    JLabel lblCenterOrEdge = new JLabel();
    lblCenterOrEdge.setText("<html>Set nut edge distance to outer string's:</html>");
    lblCenterOrEdge.setPreferredSize(new Dimension(220, 50));
    inputPanel.add(lblCenterOrEdge);

    rbEdge = new JRadioButton("Outer edge");
    rbCenter = new JRadioButton("Center");
    inputPanel.add(rbEdge);
    inputPanel.add(rbCenter);
    buttonGroupEdgeCenter = new ButtonGroup();
    buttonGroupEdgeCenter.add(rbEdge);
    buttonGroupEdgeCenter.add(rbCenter);
    rbEdge.doClick();

    btnCalculate = new JButton();
    btnCalculate.setText("Calculate");
    inputPanel.add(btnCalculate);

    jTextPaneRight = new JTextPane();
    jTextPaneRight.setDocument(new HTMLDocument());
    jTextPaneRight.setContentType("text/html");
    jTextPaneRight.setPreferredSize(outputPanelDimension);
    jTextPaneRight.setEditable(false);
    outputPanel.add(jTextPaneRight);
  }
}
