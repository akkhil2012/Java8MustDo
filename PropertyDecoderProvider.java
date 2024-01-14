public class PropertyDecoderProvider {

    private PropertyDecoderProvider() {
        // utility class
    }

    /**
     * Gets property decoder
     *
     * @param iisInstallPath non-blank path for migration from standalone IIS to WKC, null otherwise (legacy stack removal)
     * @return property decoder
     */
    public static PropertyDecoder getPropertyDecoder(String iisInstallPath) {
        return new IdentityDecoder();
    }
}

class IdentityDecoder implements  PropertyDecoder{

    public String getDecodedValue(String encodedValue) {
        if (encodedValue.isEmpty()) {
           // throw new MigrationException("Specified value is empty");
        }
        return encodedValue;
    }


}


interface PropertyDecoder {

    /**
     * Decodes the specified IIS-encrypted value
     */
    String getDecodedValue(String encodedValue);
}

