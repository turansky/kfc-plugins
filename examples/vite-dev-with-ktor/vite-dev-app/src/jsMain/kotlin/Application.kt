import react.FC
import react.Props
import react.useEffectOnce
import react.useState
import web.http.fetchAsync

internal val Application: FC<Props> = FC {
    val (data, setData) = useState<String>()

    useEffectOnce {
        fetchAsync("/api/data").await().textAsync().then {
            setData(it)
        }
    }

    +"Data from Ktor server: $data"
}
