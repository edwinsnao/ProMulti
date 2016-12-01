public class RecycleImageView extends ImageView{
	onDetachFromWindow(){
		imageloader.cancelRequest
				setImageBitmap(null);
	}
}