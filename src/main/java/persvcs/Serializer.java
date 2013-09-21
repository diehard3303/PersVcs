package persvcs;

import com.thoughtworks.xstream.XStream;

/**
 * Created with IntelliJ IDEA.
 * User: TJ (DieHard)
 * Date: 9/21/13
 * Time: 2:36 PM
 * Original Project: PersVcs
 */
public class Serializer {
    public void serializeObject(String xmlPath, Object version) {
        XStream xs = new XStream();
        String xml = "";
        byte[] bytes;
        java.io.FileOutputStream fos = null;

        try {
            xml = xs.toXML(version);
            fos = new java.io.FileOutputStream(xmlPath);
            bytes = xml.getBytes("UTF-8");
            fos.write(bytes);
        } catch (Exception e) {
            System.err.println("Error in XML Write: " + e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
