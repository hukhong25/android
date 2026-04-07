package vn.haui.a260330_studentsm;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private ArrayList<StudentModel> studentList;
    private Context context;
    private OnclickItemStudentListener listener;

    public StudentAdapter(Context context, ArrayList<StudentModel> studentList, OnclickItemStudentListener listener) {
        this.context = context;
        this.studentList = studentList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // "Bơm" layout cho 1 item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        // Đổ dữ liệu của từng sinh viên vào View tương ứng
        StudentModel student = studentList.get(position);

        holder.tvStudentName.setText(student.getName());
        holder.tvStudentInfo.setText("NS: " + student.getDob() + " | SĐT: " + student.getPhone());
        holder.tvStudentEmail.setText(student.getEmail());
        // Sử dụng ảnh đại diện mặc định
        holder.imgAvatar.setImageResource(android.R.drawable.sym_def_app_icon);

       /*KICH HOAT SU KIEN*/
        // Sự kiện trên ImageButtỏnemove
        holder.iButtonRemove.setOnClickListener(v->{
            if(listener!=null) {
                listener.onClickImagButtonRemoveListener(position);
            }
        });
        //Su kien tren item
        holder.itemView.setOnClickListener(v->{
            if(listener!=null){
                listener.onClickItemStudentListener(position);
            }
        });
        // Su kien tren anh avarta
        holder.imgAvatar.setOnClickListener(v->{
            if(listener!=null){
                listener.onClickAvatarStudentListener(position);
            }
        });
        // --- BẮT SỰ KIỆN CLICK VÀO ITEM ---
       /* holder.itemView.setOnClickListener(v -> {
            String details = "Thông tin chi tiết:\n" +
                    "Họ tên: " + student.getName() + "\n" +
                    "Ngày sinh: " + student.getDob() + "\n" +
                    "SĐT: " + student.getPhone() + "\n" +
                    "Email: " + student.getEmail();

            Toast.makeText(context, details, Toast.LENGTH_LONG).show();
        });*/

//
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    // --- ViewHolder Class để "nắm giữ" các View con ---
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvStudentName, tvStudentInfo, tvStudentEmail;
        ImageButton iButtonRemove;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            tvStudentName = itemView.findViewById(R.id.tv_StudentName);
            tvStudentInfo = itemView.findViewById(R.id.tv_student_info);
            tvStudentEmail = itemView.findViewById(R.id.tv_student_email);
            iButtonRemove=itemView.findViewById(R.id.img_button_remove);
        }
    }
    public interface OnclickItemStudentListener{
        void onClickItemStudentListener(int position);
        void onClickAvatarStudentListener(int position);
        void onClickImagButtonRemoveListener(int position);
    }
}
