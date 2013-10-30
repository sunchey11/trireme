/**
 * Copyright 2013 Apigee Corporation.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.apigee.trireme.net.spi;

import java.nio.ByteBuffer;

public interface HttpResponseAdapter
    extends HttpMessageAdapter
{
    int getStatusCode();
    void setStatusCode(int code);

    /**
     * Send the headers, and optionally the data if the data was already
     * set on this object. Return true if the I/O completed right away.
     */
    HttpFuture send(boolean lastChunk);

    /**
     * Send just a chunk of data. If "send" was not called first, then
     * the behavior is undefined. Return true if the I/O completed right away.
     */
    HttpFuture sendChunk(ByteBuffer data, boolean lastChunk);

    /**
     * A fatal error occurred while processing the associated request. The adapter should respond with
     * an error.
     *
     * @param message the error message generated by the script
     * @param stack if available, the JavaScript stack trace, otherwise null
     */
    void fatalError(String message, String stack);

    /**
     * Add a trailer -- only valid on a chunked message and sent on the last chunk.
     */
    void setTrailer(String name, String value);

    /**
     * Destroy the request prematurely -- this is not required unless there is a problem on the
     * response side.
     */
    void destroy();
}
