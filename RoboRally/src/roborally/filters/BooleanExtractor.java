package roborally.filters;

import roborally.model.Element;

public interface BooleanExtractor {
    public boolean isSatisfied(Element element);
}
