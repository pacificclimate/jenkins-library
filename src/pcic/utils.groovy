package pcic


def getPipString(int pythonVersion) {
    String pip
    if (pythonVersion == 3) {
        pip = 'pip3'
    } else {
        pip = 'pip'
    }

    return pip
}


static Map applyOptionalParameters(defaultMap, newMap) {
    def unknownKeys = (defaultMap.keySet() + newMap.keySet()) - defaultMap.keySet()

    if (unknownKeys.size() > 0) {
        throw new Exception("Key(s) ${unknownKeys} not available in ${defaultMap.keySet()}")
    }

    return defaultMap + newMap
}
