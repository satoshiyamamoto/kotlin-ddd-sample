package htnk128.kotlin.ddd.sample.account.domain.model.account

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class EmailSpec : StringSpec({

    "正しい値の場合インスタンスを生成できる" {
        forall(
            row("a".repeat(100))
        ) { value ->
            Email.valueOf(value).toValue() shouldBe value
        }
    }

    "不正な値の場合例外となること" {
        forall(
            row(""),
            row("a".repeat(101))
        ) { value ->
            shouldThrow<AccountInvalidRequestException> {
                Email.valueOf(value)
            }
        }
    }
})
