FROM mysql:8.0.27

ADD my.cnf /etc/mysql/conf.d/
RUN chmod 644 /etc/mysql/conf.d/my.cnf
# ホストのmy.cnfをbootした上で、フル権限を剥奪する
# mysqlがフル権限のmy.cnfファイルを無視するためである(windows限定？？)
# refs: https://qiita.com/koyo-miyamura/items/4d1430b9086c5d4a58a5