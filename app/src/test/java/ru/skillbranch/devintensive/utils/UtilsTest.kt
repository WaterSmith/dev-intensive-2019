package ru.skillbranch.devintensive.utils

import org.junit.Assert.*
import org.junit.Test

class UtilsTest {
    @Test
    fun parseFullNameTest(){
        /* skillBranch tests */
        assertEquals(null to null, Utils.parseFullName(null))
        assertEquals(null to null, Utils.parseFullName(""))
        assertEquals(null to null, Utils.parseFullName(" "))
        assertEquals("John" to null, Utils.parseFullName("John"))
        /* additional tests */
        assertEquals(null to null, Utils.parseFullName("     "))
        assertEquals("null" to null, Utils.parseFullName("null"))
        assertEquals("John" to null, Utils.parseFullName("John      "))
        assertEquals("John" to null, Utils.parseFullName(" John     "))
    }

    @Test
    fun toInitialsTest(){
        /* skillBranch tests */
        assertEquals("JD", Utils.toInitials("john", "doe"))
        assertEquals("J", Utils.toInitials("John", null))
        assertEquals(null, Utils.toInitials(null, null))
        assertEquals(null, Utils.toInitials(" ", ""))

        /* additional tests */
        assertEquals(null, Utils.toInitials(" ", null))
        assertEquals(null, Utils.toInitials(null, ""))
        assertEquals("T", Utils.toInitials(null, "  tommy"))
        assertEquals("ST", Utils.toInitials("  samuel  ", "  tommy"))
        assertEquals("J", Utils.toInitials(null, "John"))
    }

    @Test
    fun transliteration_test() {
        /* skillBranch tests */
        assertEquals("Zhenya Stereotipov", Utils.transliteration("Женя Стереотипов"))
        assertEquals("Amazing_Petr", Utils.transliteration("Amazing Петр","_"))

        /* additional tests */
        assertEquals("iVan Stereotizhov", Utils.transliteration("иВан Стереотижов"))
        assertEquals("Amazing_PeZhr", Utils.transliteration("Amazing ПеЖр", "_"))
        assertEquals("aAbBvVgGdDeEeEzhZhzZiIiIkKlL", Utils.transliteration("аАбБвВгГдДеЕёЁжЖзЗиИйЙкКлЛ"))
        assertEquals("mMnNoOpPrRsStTuUfFhHcCshShsh'Sh'", Utils.transliteration("мМнНоОпПрРсСтТуУфФхХцЦшШщЩ"))
        assertEquals("eEyuYuyaYa", Utils.transliteration("ъЪьЬэЭюЮяЯ"))
        assertEquals("123|!,^-=+><|english", Utils.transliteration("123 !,^-=+>< english", "|"))
        assertEquals("Zhizha ZhiZhnaYa", Utils.transliteration("Жижа ЖиЖнаЯ"))
        assertEquals("Sobaka is a dog", Utils.transliteration("Собака dog", " is a "))
    }

    @Test
    fun checkGitHub_test_positive_case() {
        var url = "https://github.com/watersmith"
        assertTrue(Utils.mathGitHubAccount(url))
        url = "https://github.com/johnDoe"
        assertTrue(Utils.mathGitHubAccount(url))
        url = "https://www.github.com/johnDoe"
        assertTrue(Utils.mathGitHubAccount(url))
        url = "www.github.com/johnDoe"
        assertTrue(Utils.mathGitHubAccount(url))
        url = "github.com/johnDoe"
        assertTrue(Utils.mathGitHubAccount(url))
    }

    @Test
    fun checkGitHub_test_case_unsensitive() {
        var url = "HTTPS://www.GitHub.COM/JohnDoe"
        assertTrue(Utils.mathGitHubAccount(url))
    }

    @Test
    fun checkGitHub_test_negative_case() {
        var url = "https://anyDomain.github.com/johnDoe"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com/"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com/johnDoe/tree"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com/johnDoe/tree/something"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "github.com/enterprise"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "github.com/pricing"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "github.com/join"
        assertFalse(Utils.mathGitHubAccount(url))
    }

    @Test
    fun checkGitHub_test_reserved_worlds(){
        var url = "https://github.com/enterprise"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com/features"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com/topics"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com/collections"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com/trending"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com/events"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com/marketplace"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com/pricing"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com/nonprofit"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com/customer-stories"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com/security"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com/login"
        assertFalse(Utils.mathGitHubAccount(url))
        url = "https://github.com/join"
        assertFalse(Utils.mathGitHubAccount(url))
    }
}
