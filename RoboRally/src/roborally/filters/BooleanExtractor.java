package roborally.filters;

import roborally.model.Element;

/**
 * @author Ben Adriaenssens (ben.adriaenssens@student.kuleuven.be) - WtkCws,
 *         Toon Nolten (toon.nolten@student.kuleuven.be) - CwsElt.
 */
public interface BooleanExtractor {
    public boolean isSatisfied(Element element);
}
