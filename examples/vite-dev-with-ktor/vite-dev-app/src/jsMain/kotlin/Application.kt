import react.FC
import react.Props
import react.useEffectOnce
import react.useState
import web.http.fetch

internal val Application: FC<Props> = FC {
    val (data, setData) = useState<String>()

    useEffectOnce {
        val newData = fetch("/api/data").text()

        setData(newData)
    }

    +"Data from Ktor server: $data"
}
