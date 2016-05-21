package engineer.carrot.warren.kale.irc.message.ircv3

import engineer.carrot.warren.kale.irc.CharacterCodes
import engineer.carrot.warren.kale.irc.message.IMessage
import engineer.carrot.warren.kale.irc.message.IMessageFactory
import engineer.carrot.warren.kale.irc.message.IrcMessage

data class CapReqMessage(val target: String? = null, val caps: List<String>): IMessage {

    companion object Factory: IMessageFactory<CapReqMessage> {
        override val messageType = CapReqMessage::class.java
        override val key = "CAPREQ"

        override fun serialise(message: CapReqMessage): IrcMessage? {
            val caps = message.caps.joinToString(separator = CharacterCodes.SPACE.toString())

            if (message.target != null) {
                return IrcMessage(command = "CAP", parameters = listOf(message.target, "REQ", caps))
            } else {
                return IrcMessage(command = "CAP", parameters = listOf("REQ", caps))
            }
        }

        override fun parse(message: IrcMessage): CapReqMessage? {
            if (message.parameters.size < 3) {
                return null
            }

            val target = message.parameters[0]
            val subCommand = message.parameters[1]
            val rawCaps = message.parameters[2]

            val caps = rawCaps.split(delimiters = CharacterCodes.SPACE).filterNot { it.isEmpty() }

            return CapReqMessage(target = target, caps = caps)
        }
    }

}