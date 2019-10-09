
public class TileList {
	private Tile[] tileLisTiles;
	private int currentSize;

	public TileList() {
		tileLisTiles = new Tile[8];
		currentSize = 0;
	}

	public int size() {
		return currentSize;
	}

	public void add(Tile newTile) {
		if (newTile == null) {
			System.out.println("Warning: The tile could't be null!");
		} else if (currentSize >= 8) {
			System.out.println("Error: Over range!");
		} else {
			tileLisTiles[currentSize] = newTile;
			currentSize++;
		}
	}

	public Tile get(int index) {
		if (index < 0 || index >= 8) {
			System.out.println("Warning: index is valid!");
			return null;
		} else {
			return tileLisTiles[index];
		}

	}
}
