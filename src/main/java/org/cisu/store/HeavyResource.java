package org.cisu.store;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy // With @Lazy java doesn't create the class until is needed.
public class HeavyResource {
    public HeavyResource() {
        System.out.println("HeavyResource created");
    }
}
