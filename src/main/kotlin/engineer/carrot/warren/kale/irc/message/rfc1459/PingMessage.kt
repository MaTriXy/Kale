package engineer.carrot.warren.kale.irc.message.rfc1459

import engineer.carrot.warren.kale.irc.message.IMessage
import engineer.carrot.warren.kale.irc.message.IMessageFactory
import engineer.carrot.warren.kale.irc.message.IrcMessage

data class PingMessage(val token: String): IMessage {

    companion object Factory: IMessageFactory<PingMessage> {
        override val messageType = PingMessage::class.java
        override val command = "PING"

        override fun serialise(message: PingMessage): IrcMessage? {
            return IrcMessage(command = PingMessage.command, parameters = listOf(message.token))
        }

        override fun parse(message: IrcMessage): PingMessage? {
            if (message.parameters.size < 1) {
                return null
            }

            return PingMessage(token = message.parameters[0])
        }
    }

}