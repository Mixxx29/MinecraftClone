package org.example.texture;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {
    private final int id;

    public Texture(String filename) {
        PNGDecoder decoder;
        ByteBuffer buffer;
        try {
            decoder = new PNGDecoder(
                    Texture.class.getResourceAsStream(filename)
            );

            buffer = ByteBuffer.allocateDirect(
                    4 * decoder.getWidth() * decoder.getHeight()
            );
            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexImage2D(
                GL_TEXTURE_2D,
                0,
                GL_RGBA,
                decoder.getWidth(),
                decoder.getHeight(),
                0,
                GL_RGBA,
                GL_UNSIGNED_BYTE,
                buffer
        );

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public int getId() {
        return id;
    }
}
