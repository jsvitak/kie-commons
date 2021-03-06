package org.kie.commons.java.nio.base.version;

import java.util.Date;

/**
 *
 */
public interface VersionRecord {

    String id();

    String author();

    String comment();

    Date date();

    String uri();
}
