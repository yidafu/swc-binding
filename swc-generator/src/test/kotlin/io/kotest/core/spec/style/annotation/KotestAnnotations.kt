package io.kotest.core.spec.style.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Test(
    val enabled: Boolean = true,
    val tags: Array<String> = []
)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BeforeTest(
    val enabled: Boolean = true,
    val tags: Array<String> = []
)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AfterTest(
    val enabled: Boolean = true,
    val tags: Array<String> = []
)

