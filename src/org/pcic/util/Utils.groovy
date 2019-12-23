package org.pcic.util


class Utils implements Serializable {

    Map processParams(Map expected, Map args) {
        def unknownKeys = (expected.keySet() + args.keySet()) - expected.keySet()

        if (unknownKeys.size() > 0) {
            throw new Exception("Key(s) ${unknownKeys} not available in ${expected.keySet()}")
        }

        return expected + args
    }
}
