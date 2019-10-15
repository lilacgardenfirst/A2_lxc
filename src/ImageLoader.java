import java.util.Hashtable; // HashTable
import javax.imageio.*; // ImageIO
import java.awt.image.BufferedImage; // BufferedImage
import java.io.*; // File

// The ImageLoader class impliments a hashtable to efficiently cache images that are loaded from file. 
// The File is loaded into the hashtable the first time it is accessed then accessed by filename after that
// Returned a BufferedImage
// Tested with .PNG files. Keep the size low ( I am using 256x256 for the board tiles and even that is large!)
public class ImageLoader{
	
	// File Names
	private static String sampleFile = "Alive.png"; // Basic file for testing
	private static final String path = "src\\Resources\\"; // The Images must be in the Resources folder at the ROOT folder of your project (might be different in IntelliJ or Eclipse!)

	// Hashtable is a datastructure that stores key value pairs. See COMP 2140 for details
	private static Hashtable<String,BufferedImage> images = new Hashtable<String,BufferedImage>();

	// Test the Image Loader. 
	public static void main(String[] args){
		// Testing files:
		BufferedImage bi = loadImage(sampleFile);

		if(bi != null){
			System.out.println("OK so far");
		}else{
			System.out.println("Image Test didn't work!");
		}

	}

	// Get image should just retrieve the cached image (by filename) or attenpts to load it
	// May throw an IO exception if no image is found. 
	public static BufferedImage loadImage(String fileName){
		// Access the BufferedImage cached in the hashtable
		// Stored by the full filename e.g. "File.png"
		BufferedImage tempImage = images.get(fileName);

		// If the image is not found, load it from file and cache it in the hashtable
		if( tempImage == null){
			try{
				// Attempt to load the file at the default path and cache it within the hashtable
				tempImage = ImageIO.read(new File(path, fileName));
				// Cache it in the hashtable (if it finds a file)
				images.put(fileName, tempImage);
			}catch(IOException e){
				System.out.println("Exception Thrown: Cannot Load Image: " + path + fileName);
				System.out.println(e.toString());
				e.printStackTrace();

				// blank buffered image as default
				tempImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
			}
		}

		if(tempImage == null){
			System.out.println("Something went wrong when loading " + path + fileName);
		}

		return tempImage;
	}
}