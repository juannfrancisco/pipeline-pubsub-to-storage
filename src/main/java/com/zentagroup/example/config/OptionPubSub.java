package com.zentagroup.example.config;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.StreamingOptions;
import org.apache.beam.sdk.options.Validation;

public interface OptionPubSub extends PipelineOptions, StreamingOptions {

    @Description("Nombre del topico al cual desea suscribirse")
    @Validation.Required
    String getTopicName();
    void setTopicName(String topicName);

    @Description("Nombre del bucket destino para almacenar los eventos")
    @Validation.Required
    String getOutput();
    void setOutput(String output);
}
