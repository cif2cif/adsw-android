
package es.upm.dit.adsw.listacompra;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class ProductoDbAdaptador {

	public static final String COL_NOMBRE = "nombre";
	public static final String COL_CANTIDAD = "cantidad";
	public static final String COL_ID = "_id";

	private static final String TAG = ProductoDbAdaptador.class.getSimpleName();
	private DBHelper dbHelper;
	private SQLiteDatabase db;

	private static final String DATABASE_NAME = "lista_compra";
	private static final String DATABASE_TABLE = "productos";
	
	private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + " (_id integer primary key autoincrement, "
			+ "nombre text not null, cantidad integer not null);";

	
	private static final int DATABASE_VERSION = 1;

	private final Context mCtx;
	
	
	private static class DBHelper extends SQLiteOpenHelper {

		DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i(TAG, "Creando base de datos");
			db.execSQL(DATABASE_CREATE);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
			Log.w(TAG, "Actualizando base de datos de la versión " + versionAnterior + " a "
					+ versionNueva + ", lo que destruirá todos los datos existentes");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db); // en desarrollo, vuelvo a crear BD en vez de cambiar de versión
		}
	}
	  /**
     * Constructor - recibe el contexto de la base de datos que va a ser
     * abierta o creada. Abre la base de datos. Si no se puede abrir, intenta crear una nueva
     * instancia de la base de datos. Si no se puede crear, lanza una excepción
     * para alertar del fallo
     * 
     * @param ctx contexto con el que trabajar
     * @throws SQLException si la base de datos no estuviera ni abierta ni creada
     */
    public ProductoDbAdaptador(Context ctx) throws SQLException {
        this.mCtx = ctx;
        dbHelper = new DBHelper(mCtx);
        db = dbHelper.getWritableDatabase();
    }
    
    public void close() {
        dbHelper.close();
    }


    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param nombre nombre del producto
     * @param cantidad cantidad del producto
     * @return identificador de la fila o -1 si falla
     */
    public long creaProducto(String nombre, int cantidad) {
    	Log.i(TAG, "creaProducto nombre " + nombre + " cantidad " + cantidad);
        ContentValues valoresIniciales = new ContentValues();
        valoresIniciales.put(COL_NOMBRE, nombre);
        valoresIniciales.put(COL_CANTIDAD, cantidad);

        return db.insert(DATABASE_TABLE, null, valoresIniciales);
    }

    /**
     * Borra el producto para el id dado
     * 
     * @param id de la fila de la nota que se quiere borrar
     * @return true si se borra, falso en caso contrario
     */
    public boolean borraProducto(long id) {
    	Log.i(TAG, "borra producto id " + id);
        return db.delete(DATABASE_TABLE, COL_ID + "=" + id, null) > 0;
    }

    /**
     * Devuelve un cursor sobre la lista de productos de la base de datos
     * 
     * @return Cursor sobre todos los productos
     */
    public Cursor recuperaTodosLosProductos() {

        return db.query(DATABASE_TABLE, new String[] {COL_ID, COL_NOMBRE,
                COL_CANTIDAD}, null, null, null, null, null);
    }

    /**
     * Devuelve un curso posicionado en el producto de la fila del id dado
     * 
     * @param id id del producto que se quiere recuperar
     * @return Cursor posicionado en el producto buscado, si se encuentra
     * @throws SQLException si el producto no se encuentra
     */
    public Cursor recuperaProducto(long id) throws SQLException {

        Cursor mCursor =

            db.query(true, DATABASE_TABLE, new String[] {COL_ID,
                    COL_NOMBRE, COL_CANTIDAD}, COL_ID + "=" + id, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    
    /**
     * Borra todos los productos de la tabla
     */
    public void borraTodosLosProductos() {
    	db.delete(DATABASE_TABLE, null, null);
    }

    /**
     * Actualiza el producto usando los valores proporcionados. El producto
     * que va a actualizarse se especifica mediante el id, y se cambia su contenido
     * a los valores de nombre y cantidad proporcionados.
     * 
     * @param id id  del producto que queremos actualizar
     * @param nombre nuevo nombre del producto
     * @param cantidad nueva cantidad del producto
     * @return true si el producto se actualiza correctamente, false en caso contrario
     */
    public boolean actualizaProducto(long id, String nombre, int cantidad) {
    	Log.i(TAG, "Actualiza producto nombre " + nombre + " cantidad " + cantidad);
        ContentValues args = new ContentValues();
        args.put(COL_NOMBRE, nombre);
        args.put(COL_CANTIDAD, cantidad);

        return db.update(DATABASE_TABLE, args, COL_ID + "=" + id, null) > 0;
    }
}
