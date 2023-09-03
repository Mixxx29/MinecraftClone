package org.example.mesh;

public class Face {
    private IndexGroup[] indexGroups;

    public Face(String token1, String token2, String token3) {
        indexGroups = new IndexGroup[3];
        indexGroups[0] = parseToken(token1);
        indexGroups[1] = parseToken(token2);
        indexGroups[2] = parseToken(token3);
    }

    private IndexGroup parseToken(String token) {
        IndexGroup indexGroup = new IndexGroup();

        String[] tokenParts = token.split("/");
        indexGroup.indexPosition = Integer.parseInt(tokenParts[0]) - 1;

        if (tokenParts.length > 1) {
            String textureCoords = tokenParts[1];
            if (textureCoords.length() > 0) {
                indexGroup.indexTexture = Integer.parseInt(textureCoords) - 1;
            }
        }

        if (tokenParts.length > 2) {
            indexGroup.indexNormal = Integer.parseInt(tokenParts[2]) - 1;
        }

        return indexGroup;
    }

    public IndexGroup[] getIndexGroups() {
        return indexGroups;
    }

    public static class IndexGroup {
        public static final int NO_VALUE = -1;

        public int indexPosition;
        public int indexTexture;
        public int indexNormal;

        public IndexGroup() {
            indexPosition = NO_VALUE;
            indexTexture = NO_VALUE;
            indexNormal = NO_VALUE;
        }
    }
}
