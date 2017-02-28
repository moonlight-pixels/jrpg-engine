ruleset {
    description 'CodeNarc Rules'

    ruleset('rulesets/basic.xml')
    ruleset('rulesets/braces.xml')
    ruleset('rulesets/concurrency.xml')
    ruleset('rulesets/convention.xml')
    ruleset('rulesets/design.xml')
    ruleset('rulesets/exceptions.xml')
    ruleset('rulesets/formatting.xml') {
        'ClassJavadoc' enabled: false
        'SpaceAroundMapEntryColon' {
            characterAfterColonRegex = ' '
        }
    }
    ruleset('rulesets/generic.xml')
    ruleset('rulesets/groovyism.xml')
    ruleset('rulesets/imports.xml')
    ruleset('rulesets/jdbc.xml')
    ruleset('rulesets/junit.xml')
    ruleset('rulesets/logging.xml')
    ruleset('rulesets/naming.xml') {
        'MethodName' enabled: false
    }
    ruleset('rulesets/security.xml')
    ruleset('rulesets/size.xml')
    ruleset('rulesets/unnecessary.xml') {
        'UnnecessaryObjectReferences' enabled: false
        'UnnecessaryReturnKeyword' enabled: false
        'UnnecessaryGetter' enabled: false
    }
    ruleset('rulesets/unused.xml') {
        'UnusedObject'  enabled: false
    }
}
