package ru.nsu.wallet.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    val id: Int = 0,

    @Column(name = "email")
    var email: String = "",

    @Column(name = "password")
    @get:JvmName("getPassword1")
    var password: String = "",

    @Column(name = "salt")
    var salt: String = "",

    @Column(name = "first_name")
    var firstName: String = "",

    @Column(name = "refresh_token")
    var refreshToken: String? = null,
) : UserDetails {
    override fun getAuthorities(): Set<GrantedAuthority> {
        return setOf()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return id.toString()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
