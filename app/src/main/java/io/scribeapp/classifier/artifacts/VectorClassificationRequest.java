package io.scribeapp.classifier.artifacts;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by piotrekd on 17/03/14.
 */
public class VectorClassificationRequest extends ClassificationRequest {
    public float[] vector;

    public VectorClassificationRequest(float[] vector) {
        this.vector = vector;
    }

    @Override
    public byte[] toByteArray() {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(os);
        try {
            for (float v : vector) {
                dataOutputStream.writeFloat(v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }
}
