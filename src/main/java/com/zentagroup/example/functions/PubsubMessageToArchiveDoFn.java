package com.zentagroup.example.functions;

import com.google.pubsub.v1.PubsubMessage;
import com.zentagroup.example.model.AvroPubsubMessageRecord;
import org.apache.beam.sdk.transforms.DoFn;

public class PubsubMessageToArchiveDoFn extends DoFn<PubsubMessage, AvroPubsubMessageRecord> {
    @DoFn.ProcessElement
    public void processElement(ProcessContext context) {
      PubsubMessage message = context.element();
      context.output(
          new AvroPubsubMessageRecord(
              message.getData().toByteArray(), message.getAttributesMap(), context.timestamp().getMillis()));
    }
  }