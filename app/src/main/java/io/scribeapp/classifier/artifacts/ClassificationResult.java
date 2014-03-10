package io.scribeapp.classifier.artifacts;

/**
 * Created by piotrekd on 12/29/13.
 */
public class ClassificationResult {
    private String word;

    public ClassificationResult() {}

    public ClassificationResult(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return word;
    }
}
