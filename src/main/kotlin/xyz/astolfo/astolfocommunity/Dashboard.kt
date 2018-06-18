package xyz.astolfo.astolfocommunity

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.astolfo.astolfocommunity.modules.modules

@RestController
@RequestMapping("/api")
class Dashboard(val application: AstolfoCommunityApplication) {

    @RequestMapping("/stats")
    fun stats() = Stats(application.shardManager.guildCache.size(), application.shardManager.userCache.size())

    @RequestMapping("/commands")
    fun commands() = CommandModuleMap(modules.map { module ->
        module.name to module.commands.map { command ->
            command.name to CommandData(command.description, command.usage.joinToString(separator = "\n"))
        }.toMap()
    })

    class CommandModuleMap(data: Iterable<Pair<String, Map<String, CommandData>>>) : HashMap<String, Map<String, CommandData>>(data.toMap())
    class CommandData(val desc: String, val usage: String)

    class Stats(val servers: Long, val users: Long)

}