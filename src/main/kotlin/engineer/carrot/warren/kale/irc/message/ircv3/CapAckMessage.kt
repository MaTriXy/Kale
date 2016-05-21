package engineer.carrot.warren.kale.irc.message.ircv3

import engineer.carrot.warren.kale.irc.CharacterCodes
import engineer.carrot.warren.kale.irc.message.IMessage
import engineer.carrot.warren.kale.irc.message.IMessageFactory
import engineer.carrot.warren.kale.irc.message.IrcMessage

data class CapAckMessage(val target: String? = null, val caps: List<String>): IMessage {

    companion object Factory: IMessageFactory<CapAckMessage> {
        override val messageType = CapAckMessage::class.java
        override val key = "CAPACK"

        override fun serialise(message: CapAckMessage): IrcMessage? {
            val caps = message.caps.joinToString(separator = " ")

            if (message.target != null) {
                return IrcMessage(command = "CAP", parameters = listOf(message.target, "ACK", caps))
            } else {
                return IrcMessage(command = "CAP", parameters = listOf("ACK", caps))
            }
        }

        override fun parse(message: IrcMessage): CapAckMessage? {
            if (message.parameters.size < 3) {
                return null
            }

            val target = message.parameters[0]
            val subCommand = message.parameters[1]
            val rawCaps = message.parameters[2]

            val caps = rawCaps.split(delimiters = CharacterCodes.SPACE).filterNot { it.isEmpty() }

            return CapAckMessage(target = target, caps = caps)
        }
    }

}