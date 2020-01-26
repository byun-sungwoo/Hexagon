// Daniel Byun (byun-sungwoo)

import javax.swing.JFrame;

public class HexagonViewer {
	// [Purpose] Constructor for HexagonViewerClass
	// Constructor takes (int) width of frame, (int) height of frame, (int) size of initial hexagon.
	// Constructor takes the three integers and declares/initializes a new HexagonAnimation with the three integers plugged in respectively.
	// Constructor declares/initializes a new JFrame and adds the HexagonAnimation to the frame.
	public HexagonViewer(int width, int height, int size) {
		super();
		HexagonAnimation animation = new HexagonAnimation(width, height, size);
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setTitle("Hexagon");
		frame.setSize(animation.FRAME_WIDTH, animation.FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(animation);
	}
	
	// [Purpose] Main driver for HexagonViewer class.
	// Testing HexagonViewer by creating an instance of the class.
	public static void main(String[] args) {
		// Frame width, Frame height, Main hexagon Size
		HexagonViewer viewer = new HexagonViewer(800, 500, 300);
	}
}
