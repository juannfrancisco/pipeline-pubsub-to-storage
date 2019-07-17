package com.zentagroup.example.transforms;


import com.zentagroup.example.functions.PubsubToTextFn;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class PubSubToText extends PTransform<PCollection<PubsubMessage>, PCollection<String> > {

    @Override
    public PCollection<String> expand(PCollection<PubsubMessage> message) {
        return message.apply("", ParDo.of( new PubsubToTextFn() ));
    }
}
