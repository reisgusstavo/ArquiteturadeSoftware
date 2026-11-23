package dao;

import util.PersistenciaException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * PADRÃO SINGLETON.
 * Garante uma única instância da fábrica de conexões em toda a aplicação, que
 * guarda e reaproveita uma única Connection (em vez de abrir uma nova a cada operação).
 * As credenciais vêm de resources/db.properties, não do código.
 */
public final class ConnectionFactory {

    private static ConnectionFactory instancia;     // única instância
    private final Properties props = new Properties();
    private Connection conexao;

    private ConnectionFactory() {                    // construtor privado: ninguém dá "new"
        props.setProperty("url", "jdbc:mysql://localhost:3306/dbacademia"
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo");
        props.setProperty("user", "root");
        props.setProperty("password", "");
        File arquivo = new File("resources/db.properties");
        if (arquivo.exists()) {
            try (InputStream in = new FileInputStream(arquivo)) {
                props.load(in);
            } catch (IOException e) {
                throw new PersistenciaException("Não foi possível ler resources/db.properties", e);
            }
        }
    }

    /** Ponto de acesso global; synchronized para ser seguro com várias threads. */
    public static synchronized ConnectionFactory getInstance() {
        if (instancia == null) {
            instancia = new ConnectionFactory();
        }
        return instancia;
    }

    /** Devolve a conexão compartilhada, reabrindo-a se tiver caído. */
    public synchronized Connection getConnection() {
        try {
            if (conexao == null || conexao.isClosed() || !conexao.isValid(2)) {
                conexao = DriverManager.getConnection(
                        props.getProperty("url"), props.getProperty("user"), props.getProperty("password"));
            }
            return conexao;
        } catch (SQLException e) {
            throw new PersistenciaException("Falha ao conectar ao banco: " + e.getMessage(), e);
        }
    }

    public synchronized void fechar() {
        try {
            if (conexao != null && !conexao.isClosed()) conexao.close();
        } catch (SQLException ignorada) { /* encerrando */ }
        conexao = null;
    }
}
