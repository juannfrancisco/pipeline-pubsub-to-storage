package com.zentagroup.example.functions;

import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.DoFn;

public class PubsubToTextFn extends DoFn<PubsubMessage, String> {


    @ProcessElement
    public void processElement(ProcessContext c) {

        //Formato del string
        String message = c.element().getPayload().toString();
        c.output(message);

    }

}
