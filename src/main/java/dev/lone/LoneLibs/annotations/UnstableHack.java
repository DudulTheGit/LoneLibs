package dev.lone.LoneLibs.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * It may break in some Minecraft updates
 */
@Retention(RetentionPolicy.SOURCE)
public @interface UnstableHack
{
    String reason() default "";
}
