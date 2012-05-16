package roborally.filters;

import roborally.model.Element;

public class AllExtractor implements BooleanExtractor {

    public AllExtractor() {

    }

    @Override
    public boolean isSatisfied(Element element) {
        return true;
    }

}
