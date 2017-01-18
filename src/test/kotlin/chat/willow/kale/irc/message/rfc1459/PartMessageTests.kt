package chat.willow.kale.irc.message.rfc1459

import chat.willow.kale.irc.message.IrcMessage
import chat.willow.kale.irc.prefix.Prefix
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PartMessageTests {
    lateinit var factory: PartMessage.Factory

    @Before fun setUp() {
        factory = PartMessage
    }

    @Test fun test_parse_OneChannel() {
        val message = factory.parse(IrcMessage(command = "PART", parameters = listOf("channel1")))

        assertEquals(message, PartMessage(channels = listOf("channel1")))
    }

    @Test fun test_parse_MultipleChannels() {
        val message = factory.parse(IrcMessage(command = "PART", parameters = listOf("channel1,channel2,channel3")))

        assertEquals(message, PartMessage(channels = listOf("channel1", "channel2", "channel3")))
    }

    @Test fun test_parse_NoParameters() {
        val message = factory.parse(IrcMessage(command = "PART"))

        assertEquals(message, null)
    }

    @Test fun test_parse_SomeoneParted() {
        val message = factory.parse(IrcMessage(command = "PART", prefix = "someone@somewhere", parameters = listOf("#channel")))

        assertEquals(PartMessage(source = Prefix(nick = "someone", host = "somewhere"), channels = listOf("#channel")), message)
    }

    @Test fun test_serialise_OneChannel() {
        val message = factory.serialise(PartMessage(channels = listOf("channel1")))

        assertEquals(message, IrcMessage(command = "PART", parameters = listOf("channel1")))
    }

    @Test fun test_serialise_MultipleChannels() {
        val message = factory.serialise(PartMessage(channels = listOf("channel1", "channel2")))

        assertEquals(message, IrcMessage(command = "PART", parameters = listOf("channel1,channel2")))
    }

    @Test fun test_serialise_SomeoneParted() {
        val message = factory.serialise(PartMessage(source = Prefix(nick = "someone", host = "somewhere"), channels = listOf("#channel")))

        assertEquals(IrcMessage(command = "PART", prefix = "someone@somewhere", parameters = listOf("#channel")), message)
    }

}