package acedo.quique.decisionTree;

public class TestTreeConverter {

	private SimpleNode createTree() {
		SimpleNode root = new SimpleNode("Temperature", true);
		SimpleNode uno_uno = new SimpleNode("Humidity");
		SimpleNode uno_dos = new SimpleNode("Windy");
		SimpleNode uno_uno_uno = new SimpleNode("Outlook");
		SimpleNode uno_dos_uno = new SimpleNode("Outlook");

		root.addChild("low", uno_uno);
		root.addChild("high", uno_dos);
		uno_uno.addChild("light", uno_uno_uno);
		uno_dos.addChild("heavy", uno_dos_uno);

		uno_uno_uno.addChild("good", new SimpleNode("yes"));
		uno_uno_uno.addChild("bad", new SimpleNode("no"));

		uno_dos_uno.addChild("good", new SimpleNode("yes"));
		uno_dos_uno.addChild("bad", new SimpleNode("no"));
		uno_dos_uno.addChild("regular", new SimpleNode("maybe"));

		return root;
	}

	public static void main(String args[]) {

		TestTreeConverter mt = new TestTreeConverter();
		TreeConverter tc = new TreeConverter();
		tc.tree2graph(mt.createTree()).display();
	}

}