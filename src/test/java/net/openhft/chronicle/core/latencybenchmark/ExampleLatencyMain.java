/*
 * Copyright 2016 higherfrequencytrading.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openhft.chronicle.core.latencybenchmark;

import net.openhft.chronicle.core.Jvm;

/**
 * Created by daniel on 08/03/2016.
 */
public class ExampleLatencyMain implements LatencyTask {
    int count = 0;
    double sin;
    //private NanoSampler nanoSamplerSin;
    //private NanoSampler nanoSamplerWait;
    private LatencyTestHarness lth;

    public static void main(String[] args) {
        LatencyTestHarness lth = new LatencyTestHarness()
                .warmUp(50_000)
                .messageCount(100_000)
                .throughput(25_000)
                .accountForCoordinatedOmmission(true)
                .runs(3)
                .accountForCoordinatedOmmission(true)
                .build(new ExampleLatencyMain());
        lth.start();
    }

    @Override
    public void run(long startTimeNS) {
        count++;
        if(count==160_000) {
            System.out.println("PAUSE");
            //long now = System.nanoTime();
            Jvm.pause(1000);
            //nanoSamplerWait.sampleNanos(System.nanoTime()-now);
        }

        long now = System.nanoTime();
        sin = Math.sin(count);
        //nanoSamplerSin.sampleNanos(System.nanoTime()-now);

        lth.sample(System.nanoTime()-startTimeNS);
    }

    @Override
    public void init(LatencyTestHarness lth) {

        this.lth = lth;
        //nanoSamplerSin = lth.addProbe("sin");
        //nanoSamplerWait = lth.addProbe("busyWait");
    }

    @Override
    public void complete() {
    }
}