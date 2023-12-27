package com.todos.mmd.support;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.*;

//@Target(ElementType.TYPE)
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Inherited
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest
public @interface ServiceTest {
}
