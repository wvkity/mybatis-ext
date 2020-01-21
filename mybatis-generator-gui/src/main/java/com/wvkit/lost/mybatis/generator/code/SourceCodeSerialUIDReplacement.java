package com.wvkit.lost.mybatis.generator.code;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectStreamClass;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SourceCodeSerialUIDReplacement {

    private static final Logger LOG = LogManager.getLogger( SourceCodeSerialUIDReplacement.class );

    private static final JavaCompiler JAVA_COMPILER = ToolProvider.getSystemJavaCompiler();
    private static final StandardJavaFileManager STANDARD_JAVA_FILE_MANAGER =
            JAVA_COMPILER.getStandardFileManager( null, null, null );
    private static final Set<String> GENERATED_SERIAL_VERSION_CACHE = Collections.synchronizedSet( new HashSet<>() );
    private static final String LINE_SEPARATOR = System.getProperty( "line.separator" );
    private static final String SERIAL_UID_KEY = "private static final long serialVersionUID";

    public static Long readSerialVersionUID( String javaName, String filePath, String packageName, String binDir, String extDirs, String encoding ) {
        try {
            Map<String, byte[]> classBytes = SourceCodeCompile.compile( javaName, filePath, binDir, extDirs, encoding );
            if ( classBytes != null ) {
                MemoryClassLoader classLoader = new SourceCodeSerialUIDReplacement.MemoryClassLoader( classBytes );
                String className = packageName + "." + javaName;
                Class<?> clazz = classLoader.findClass( className );
                Long serialVersionUID = generateSerialVersionUID( clazz );
                if ( serialVersionUID != 1 ) {
                    LOG.info( "找到的class => {}, 生成对应的serialUID => {}", clazz, serialVersionUID );
                    return serialVersionUID;
                }
            }
        } catch ( Exception e ) {
            LOG.error( e );
        }
        return null;
    }

    public static boolean replace( String javaName, String filePath, String packageName, String binDir, String extDirs, String encoding ) {
        try {
            Map<String, byte[]> classBytes = SourceCodeCompile.compile( javaName, filePath, binDir, extDirs, encoding );
            if ( classBytes != null ) {
                MemoryClassLoader classLoader = new SourceCodeSerialUIDReplacement.MemoryClassLoader( classBytes );
                String className = packageName + "." + javaName;
                Class<?> clazz = classLoader.findClass( className );
                Long serialVersionUID = generateSerialVersionUID( clazz );
                LOG.info( "找到的class => {}, 生成对应的serialUID => {}", clazz, serialVersionUID );
                replace( filePath + javaName + ".java", encoding, serialVersionUID );
                return true;
            }
        } catch ( Exception e ) {
            LOG.error( e );
            // 编译失败则替换成1L
            replace( filePath + javaName + ".java", encoding, 1L );
        }
        return false;
    }

    public static void replace( Map<String, Long> serialUIDMap, String absolutePath, String encoding ) {
        if ( serialUIDMap != null ) {
            for ( Map.Entry<String, Long> entry : serialUIDMap.entrySet() ) {
                String entity = entry.getKey();
                Long serialUID = entry.getValue();
                if ( serialUID == null ) {
                    serialUID = 1L;
                }
                replace( ( absolutePath + entity + ".java" ), encoding, serialUID );
            }
        }
    }

    public static void replace( String sourceFilePath, String encoding, Long serialVersionUID ) {
        if ( !SourceCodeSerialUIDReplacement.SourceCodeCompile.isEmpty( sourceFilePath ) ) {
            StringBuffer buffer = new StringBuffer();
            File sourceFile = new File( sourceFilePath );
            // 读
            try ( InputStreamReader isr = new InputStreamReader( new FileInputStream( sourceFile ), encoding );
                  BufferedReader reader = new BufferedReader( isr ) ) {
                String tempReadLine;
                boolean isMatch = false;
                // private static final long serialVersionUID = 
                String newSerialUID = "    " + SERIAL_UID_KEY + " = " + serialVersionUID + "L;";
                while ( ( tempReadLine = reader.readLine() ) != null ) {
                    if ( !isMatch && tempReadLine.contains( SERIAL_UID_KEY ) ) {
                        isMatch = true;
                        buffer.append( newSerialUID );
                    } else {
                        buffer.append( tempReadLine );
                    }
                    buffer.append( LINE_SEPARATOR );
                }
            } catch ( Exception e ) {
                buffer = new StringBuffer( 0 );
                LOG.error( "Serialized ID replacement failed: ", e );
            }
            // 写(替换)
            if ( buffer.length() > 0 ) {
                try ( FileOutputStream fos = new FileOutputStream( sourceFile );
                      PrintWriter writer = new PrintWriter( fos ) ) {
                    writer.write( buffer.toString().toCharArray() );
                    writer.flush();
                } catch ( Exception e ) {
                    LOG.error( "Serialized ID replacement failed: ", e );
                }
            }
        }
    }

    public static Long generateSerialVersionUID( Class<?> clazz ) {
        try {
            ObjectStreamClass stream = ObjectStreamClass.lookup( clazz );
            return stream.getSerialVersionUID();
        } catch ( Exception e ) {
            LOG.error( "Serialized UID generation failed: ", e );
        }
        return 1L;
    }

    public static void clear() {
        SourceCodeSerialUIDReplacement.GENERATED_SERIAL_VERSION_CACHE.clear();
    }

    static final class SourceCodeCompile {

        public static Map<String, byte[]> compile( String javaName, String filePath, String binDir,
                                                   String extDirs, String encoding ) {
            if ( !isEmpty( javaName ) && !isEmpty( filePath ) ) {
                if ( isEmpty( encoding ) ) {
                    encoding = "UTF-8";
                }
                List<String> args = new ArrayList<>();
                args.add( "-encoding" );
                args.add( encoding );
                if ( !isEmpty( extDirs ) ) {
                    args.add( "-classpath" );
                    args.add( extDirs );
                }
                if ( !isEmpty( binDir ) ) {
                    args.add( "-d" );
                    args.add( binDir );
                }
                JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
                StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager( null, null, null );
                try ( MemoryJavaFileManager manager = new MemoryJavaFileManager( fileManager ) ) {
                    JavaFileObject javaFileObject = MemoryJavaFileManager.makeStringCode( javaName + ".java",
                            readFileContent( filePath + javaName + ".java", encoding ) );
                    JavaCompiler.CompilationTask task = javaCompiler.getTask( null, manager, null,
                            args, null, Collections.singletonList( javaFileObject ) );
                    if ( task.call() ) {
                        return manager.getClassBytes();
                    }
                } catch ( Exception e ) {
                    LOG.error( "{} source file compilation failed: ", javaName, e );
                }
            }
            return null;
        }

        public static String readFileContent( String filePath, String encoding ) {
            File file = new File( filePath );
            StringBuffer buffer = new StringBuffer();
            try ( BufferedReader bufferedReader =
                          new BufferedReader( new InputStreamReader( new FileInputStream( file ), encoding ) ) ) {
                String tempReadLine;
                while ( ( tempReadLine = bufferedReader.readLine() ) != null ) {
                    buffer.append( tempReadLine );
                    buffer.append( LINE_SEPARATOR );
                }
            } catch ( Exception e ) {
                // ignore
            }
            return buffer.toString();
        }

        public static boolean isEmpty( String value ) {
            return value == null || value.isEmpty();
        }
    }

    public static class MemoryClassLoader extends URLClassLoader {
        Map<String, byte[]> classBytesCache = new ConcurrentHashMap<>();

        public MemoryClassLoader( Map<String, byte[]> classBytes ) {
            super( new URL[ 0 ], MemoryClassLoader.class.getClassLoader() );
            this.classBytesCache.putAll( classBytes );
        }

        public Class<?> findClass( String name ) throws ClassNotFoundException {
            byte[] buffer = classBytesCache.get( name );
            if ( buffer == null ) {
                return super.findClass( name );
            }
            classBytesCache.remove( name );
            return defineClass( name, buffer, 0, buffer.length );
        }
    }

}
