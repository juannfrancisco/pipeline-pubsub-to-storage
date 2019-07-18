/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zentagroup.example;

import com.zentagroup.example.config.OptionPubSub;
import com.zentagroup.example.functions.PubsubMessageToArchiveDoFn;
import com.zentagroup.example.transforms.PubSubToText;
import com.zentagroup.example.util.DurationUtils;
import com.zentagroup.example.util.WindowedFilenamePolicy;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A starter example for writing Beam programs.
 *
 */
public class StarterPipeline {
  private static final Logger LOG = LoggerFactory.getLogger(StarterPipeline.class);

  public static void main(String[] args) {
    PipelineOptionsFactory.register(OptionPubSub.class);
    OptionPubSub options = PipelineOptionsFactory.fromArgs(args).withValidation().as(OptionPubSub.class);

    options.setStreaming(true);

    Pipeline p = Pipeline.create(options);

    p.apply("escuchando eventos", PubsubIO.readMessagesWithAttributes().fromSubscription( options.getTopicName()) )
            .apply(
                     " Window 10",
                    Window.into(FixedWindows.of(DurationUtils.parseDuration( "5s" ))))
            .apply( new PubSubToText() )
            .apply("Escribir Archivo", TextIO.write()
                    .withWindowedWrites()
                    .withNumShards(1)
                    .to(
                            new WindowedFilenamePolicy(
                                    options.getOutput(),
                                    "event",
                                    "W-P-SS-of-NN",
                                    "text")).withSuffix(".text"));


    p.run();
  }
}
