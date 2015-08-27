/**
 * Copyright 2015 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.reactivesocket.internal;

import io.reactivesocket.Frame;
import uk.co.real_logic.agrona.MutableDirectBuffer;
import uk.co.real_logic.agrona.concurrent.OneToOneConcurrentArrayQueue;
import uk.co.real_logic.agrona.concurrent.UnsafeBuffer;

import java.nio.ByteBuffer;

public class PooledFrame
{
    private static final ThreadLocal<OneToOneConcurrentArrayQueue<Frame>> PER_THREAD_FRAME_QUEUE =
        ThreadLocal.withInitial(() -> new OneToOneConcurrentArrayQueue<>(16));

    private static final ThreadLocal<OneToOneConcurrentArrayQueue<MutableDirectBuffer>> PER_THREAD_DIRECTBUFFER_QUEUE =
        ThreadLocal.withInitial(() -> new OneToOneConcurrentArrayQueue<>(16));

    public Frame acquireFrame(int size)
    {
        return Frame.allocate(new UnsafeBuffer(ByteBuffer.allocate(size)));
    }

    public Frame acquireFrame(ByteBuffer byteBuffer)
    {
        return Frame.allocate(new UnsafeBuffer(byteBuffer));
    }

    public void release(Frame frame)
    {
    }

    public Frame acquireFrame(MutableDirectBuffer mutableDirectBuffer)
    {
        return Frame.allocate(mutableDirectBuffer);
    }

    public MutableDirectBuffer acquireMutableDirectBuffer(ByteBuffer byteBuffer)
    {
        return new UnsafeBuffer(byteBuffer);
    }

    public MutableDirectBuffer acquireMutableDirectBuffer(int size)
    {
        return new UnsafeBuffer(ByteBuffer.allocate(size));
    }

    public void release(MutableDirectBuffer mutableDirectBuffer)
    {
    }
}
